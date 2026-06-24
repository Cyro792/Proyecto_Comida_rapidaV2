package com.Microservicio.Inventario.MicroservicioInventario;

import com.Microservicio.Inventario.MicroservicioInventario.model.Producto;
import com.Microservicio.Inventario.MicroservicioInventario.repository.ProductoRepository;
import com.Microservicio.Inventario.MicroservicioInventario.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MicroservicioInventarioApplicationTests {

	@Mock
	private ProductoRepository productoRepository;

	@InjectMocks
	private ProductoService productoService;

	@Test
	void testListarTodosLosProductos() {
		// 1. Creamos datos falsos para la prueba
		Producto p1 = new Producto(1L, "Papas Fritas", 2500.0, 50);
		Producto p2 = new Producto(2L, "Hamburguesa", 4500.0, 30);

		// 2. Simulamos el comportamiento de la base de datos
		when(productoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

		// 3. Ejecutamos el servicio
		List<Producto> resultado = productoService.listarTodos();

		// 4. Comprobamos que el resultado sea correcto con JUnit 5
		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertEquals("Papas Fritas", resultado.get(0).getNombre());
	}
}