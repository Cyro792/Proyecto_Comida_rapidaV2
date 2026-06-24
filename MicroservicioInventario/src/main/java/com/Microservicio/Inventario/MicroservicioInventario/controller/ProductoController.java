package com.Microservicio.Inventario.MicroservicioInventario.controller;

import com.Microservicio.Inventario.MicroservicioInventario.dto.ProductoRequestDTO;
import com.Microservicio.Inventario.MicroservicioInventario.model.Producto;
import com.Microservicio.Inventario.MicroservicioInventario.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Endpoint para listar todos los productos (Modificado con HATEOAS)
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> listarProductos() {
        List<EntityModel<Producto>> productosHateoas = productoService.listarTodos().stream()
                .map(producto -> EntityModel.of(producto,
                        linkTo(methodOn(ProductoController.class).listarProductos()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Producto>> rootModel = CollectionModel.of(productosHateoas,
                linkTo(methodOn(ProductoController.class).listarProductos()).withSelfRel());

        return ResponseEntity.ok(rootModel);
    }

    // Endpoint para crear un producto (Modificado con HATEOAS)
    @PostMapping
    public ResponseEntity<EntityModel<Producto>> crearProducto(@Valid @RequestBody ProductoRequestDTO productoDTO) {
        Producto nuevoProducto = productoService.guardarProducto(productoDTO);

        // Creamos el modelo HATEOAS y le añadimos un link hacia el listado general como referencia
        EntityModel<Producto> productoModel = EntityModel.of(nuevoProducto,
                linkTo(methodOn(ProductoController.class).listarProductos()).withRel("lista-productos"));

        return new ResponseEntity<>(productoModel, HttpStatus.CREATED);
    }
}