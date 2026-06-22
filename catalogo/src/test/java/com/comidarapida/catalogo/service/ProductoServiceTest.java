package com.comidarapida.catalogo.service;

import com.comidarapida.catalogo.dto.ProductoDTO;
import com.comidarapida.catalogo.model.Producto;
import com.comidarapida.catalogo.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository; // Simulamos la base de datos

    @InjectMocks
    private ProductoService productoService; // Inyectamos el mock en tu servicio real

    @Test
    void obtenerTodos_DebeRetornarListaDeProductos() {
        // GIVEN (Dado que tenemos un producto en la base de datos)
        Producto producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombre("Hamburguesa");
        producto.setDescripcion("Clásica con queso");
        producto.setPrecio(5000.0);
        when(productoRepository.findAll()).thenReturn(List.of(producto));

        // WHEN (Cuando llamamos al método obtenerTodos)
        List<ProductoDTO> resultado = productoService.obtenerTodos();

        // THEN (Entonces verificamos que la lista tenga 1 elemento y sea la hamburguesa)
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Hamburguesa", resultado.get(0).getNombre());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void guardarProducto_DebeRetornarProductoCreado() {
        // GIVEN (Dado que enviamos un DTO para crear un producto)
        ProductoDTO dtoEntrada = new ProductoDTO(null, "Pizza", "Familiar pepperoni", 10000.0);

        Producto productoGuardado = new Producto();
        productoGuardado.setIdProducto(2L);
        productoGuardado.setNombre("Pizza");
        productoGuardado.setDescripcion("Familiar pepperoni");
        productoGuardado.setPrecio(10000.0);

        when(productoRepository.save(any(Producto.class))).thenReturn(productoGuardado);

        // WHEN (Cuando guardamos el producto)
        ProductoDTO resultado = productoService.guardarProducto(dtoEntrada);

        // THEN (Entonces el resultado debe tener un ID asignado y coincidir con los datos)
        assertNotNull(resultado);
        assertEquals(2L, resultado.getIdProducto());
        assertEquals("Pizza", resultado.getNombre());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void actualizarProducto_CuandoExiste_DebeRetornarProductoActualizado() {
        // GIVEN (Dado que el producto existe y queremos actualizarlo)
        Long id = 1L;
        ProductoDTO dtoEntrada = new ProductoDTO(null, "Completo Italiano", "Con extra palta", 3500.0);

        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(id);
        productoExistente.setNombre("Completo Simple");

        Producto productoActualizado = new Producto();
        productoActualizado.setIdProducto(id);
        productoActualizado.setNombre("Completo Italiano");
        productoActualizado.setDescripcion("Con extra palta");
        productoActualizado.setPrecio(3500.0);

        when(productoRepository.findById(id)).thenReturn(Optional.of(productoExistente));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoActualizado);

        // WHEN (Cuando solicitamos la actualización)
        ProductoDTO resultado = productoService.actualizarProducto(id, dtoEntrada);

        // THEN (Entonces los datos retornados deben ser los nuevos)
        assertNotNull(resultado);
        assertEquals("Completo Italiano", resultado.getNombre());
        assertEquals(3500.0, resultado.getPrecio());
        verify(productoRepository, times(1)).findById(id);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void actualizarProducto_CuandoNoExiste_DebeLanzarExcepcion() {
        // GIVEN (Dado que buscamos un ID que no existe en la BD)
        Long id = 99L;
        ProductoDTO dtoEntrada = new ProductoDTO(null, "Fantasma", "No existe", 100.0);
        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN (Cuando intentamos actualizar, Entonces debe lanzar una excepción)
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.actualizarProducto(id, dtoEntrada);
        });

        // Verificamos que el mensaje de error sea exactamente el que programaste
        assertEquals("Error!! El producto no existe", exception.getMessage());
        verify(productoRepository, times(1)).findById(id);
        verify(productoRepository, never()).save(any(Producto.class)); // Aseguramos que nunca intentó guardar
    }

    @Test
    void eliminarProducto_DebeLlamarAlRepositorio() {
        // GIVEN (Dado un ID a eliminar)
        Long id = 1L;
        doNothing().when(productoRepository).deleteById(id);

        // WHEN (Cuando llamamos al método eliminar)
        productoService.eliminarProducto(id);

        // THEN (Entonces verificamos que el repositorio ejecutó la orden de borrado)
        verify(productoRepository, times(1)).deleteById(id);
    }
}














