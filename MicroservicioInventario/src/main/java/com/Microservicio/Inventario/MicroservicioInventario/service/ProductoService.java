package com.Microservicio.Inventario.MicroservicioInventario.service;

import com.Microservicio.Inventario.MicroservicioInventario.dto.ProductoRequestDTO;
import com.Microservicio.Inventario.MicroservicioInventario.model.Producto;
import com.Microservicio.Inventario.MicroservicioInventario.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private ProductoRepository productoRepository;

    // Listar todos los productos
    public List<Producto> listarTodos() {
        logger.info("Obteniendo lista completa de productos en inventario");
        return productoRepository.findAll();
    }

    // Crear o guardar un producto usando el DTO
    public Producto guardarProducto(ProductoRequestDTO productoDTO) {
        logger.info("Procesando registro de nuevo producto: {}", productoDTO.getNombre());

        // Pasamos los datos del DTO a una de Base de Datos
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());

        Producto productoGuardado = productoRepository.save(producto);
        logger.info("Producto guardado exitosamente con ID: {}", productoGuardado.getId());

        return productoGuardado;
    }
}