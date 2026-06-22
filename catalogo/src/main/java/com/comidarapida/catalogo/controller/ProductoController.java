package com.comidarapida.catalogo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.comidarapida.catalogo.dto.ProductoDTO;
import com.comidarapida.catalogo.dto.UsuarioResponseDTO;
import com.comidarapida.catalogo.service.ProductoService;
import com.comidarapida.catalogo.client.PagoClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Gestión de Catálogo", description = "Endpoints para administrar los productos de pedidos.comidarapida.cl")
public class ProductoController {

    private final ProductoService productoService;
    private final PagoClient pagoClient;

    @Operation(summary = "Probar conexión con Inventario", description = "Verifica la comunicación interna con el microservicio de inventario.")
    @GetMapping("/integracion-inventario")
    public ResponseEntity<List<Object>> probarConexionInventario() {
        return ResponseEntity.ok(productoService.traerInventario());
    }

    @Operation(summary = "Listar todos los productos", description = "Retorna una lista completa de los productos disponibles en el catálogo.")
    @ApiResponse(responseCode = "200", description = "Lista recuperada exitosamente")
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarProducto(){
        log.info("Ejecutando petición para listar todos los productos del catálogo");

        List<ProductoDTO> productos = productoService.obtenerTodos();

        // Inyectando HateOAS a cada producto de la lista
        for (ProductoDTO producto : productos) {
            // Enlace hacia sí mismo (Self)
            producto.add(linkTo(methodOn(ProductoController.class).actualizarProducto(producto.getIdProducto(), null)).withSelfRel());
            // Enlace hacia la lista general
            producto.add(linkTo(methodOn(ProductoController.class).listarProducto()).withRel("productos"));
        }

        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Crear un nuevo producto", description = "Añade un nuevo producto al catálogo validando las reglas de negocio.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO){
        log.info("Recibiendo petición para crear un nuevo producto: {}", productoDTO.getNombre());
        ProductoDTO nuevoProducto = productoService.guardarProducto(productoDTO);
        log.info("Producto creado exitosamente con ID: {}", nuevoProducto.getIdProducto());
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar producto existente", description = "Modifica los datos de un producto específico según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(
            @Parameter(description = "ID del producto a actualizar") @PathVariable Long id,
            @Valid @RequestBody ProductoDTO productoDTO){
        log.info("Recibiendo petición para actualizar el producto con ID: {}", id);
        ProductoDTO productoActualizado = productoService.actualizarProducto(id, productoDTO);
        log.info("Producto actualizado exitosamente con ID: {}", id);
        return ResponseEntity.ok(productoActualizado);
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto del catálogo de forma permanente usando su ID.")
    @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(
            @Parameter(description = "ID numérico del producto a eliminar") @PathVariable Long id){
        log.warn("Recibiendo petición para eliminar el producto con ID: {}", id);
        productoService.eliminarProducto(id);
        log.info("Producto eliminado exitosamente con ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Probar Feign Client con Usuarios", description = "Se comunica por red con el microservicio de Usuarios para validar un ID específico.")
    @GetMapping("/prueba-feign/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> probarFeign(
            @Parameter(description = "ID del usuario a buscar en el microservicio externo") @PathVariable Long idUsuario){
        log.info("Catálogo está intentando comunicarse con Usuarios para buscar el ID: {}", idUsuario);
        UsuarioResponseDTO usuarioEncontrado = productoService.probarConexionConUsuarios(idUsuario);
        return ResponseEntity.ok(usuarioEncontrado);
    }

    @Operation(summary = "Simular pago de producto", description = "Redirige la solicitud de compra hacia el microservicio de Pagos.")
    @PostMapping("/comprar")
    public ResponseEntity<Object> simularCompra(@RequestBody Map<String, Object> requestPago) {
        log.info("Catálogo redirigiendo el pago al microservicio de Brandon...");
        Object respuestaPago = pagoClient.procesarPago(requestPago);
        return ResponseEntity.ok(respuestaPago);
    }

    @Operation(summary = "Ver historial general de pagos", description = "Solicita la lista completa de transacciones al microservicio de Pagos.")
    @GetMapping("/historial-pagos")
    public ResponseEntity<List<Object>> verHistorialDePagos() {
        log.info("Catálogo solicitando el historial completo de pagos a Brandon...");
        List<Object> historial = pagoClient.obtenerHistorialPagos();
        return ResponseEntity.ok(historial);
    }
    @Operation(summary = "Generar datos masivos (DataFake)",description = "pobla la base de datos con una cantidad especifica de productos falsos para pruebas")
    @PostMapping("/generar-datos/{cantidad}")
    public ResponseEntity<String>generarDatosMasivos(
            @Parameter(description = "cantidad de productos a generar")@PathVariable int cantidad){
        log.info("generando {} productos de prueba con DataFaker...",cantidad);
        productoService.generarDatosFalsos(cantidad);
        return ResponseEntity.ok("Exito! Se generaron "+ cantidad +"productos aleatorios en la base de datos.");
    }




}