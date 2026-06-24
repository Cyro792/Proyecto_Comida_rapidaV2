package com.pagos.microservicio;

import com.pagos.microservicio.dto.PagoRequestDTO;
import com.pagos.microservicio.model.Pago;
import com.pagos.microservicio.repository.PagoRepository;
import com.pagos.microservicio.service.PagoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // <--- ESTO ACTIVA MOCKITO IGUAL QUE EN INVENTARIO
class MicroservicioPagosApplicationTests {

	@Mock
	private PagoRepository pagoRepository;

	@InjectMocks
	private PagoService pagoService;

	@Test
	void testProcesarPagoExitoso() {
		// 1. Creamos el DTO de entrada real que pide tu método
		PagoRequestDTO dto = new PagoRequestDTO();
		dto.setOrdenId(101L);
		dto.setMonto(2500.0);
		dto.setMetodoPago("TARJETA");

		// 2. Creamos el objeto Pago simulado que debería retornar el repositorio
		Pago pagoSimulado = new Pago();
		pagoSimulado.setId(1L);
		pagoSimulado.setOrdenId(101L);
		pagoSimulado.setMonto(2500.0);
		pagoSimulado.setMetodoPago("TARJETA");
		pagoSimulado.setEstado("APROBADO");

		// 3. Simulamos el comportamiento del repositorio con Mockito
		when(pagoRepository.save(any(Pago.class))).thenReturn(pagoSimulado);

		// 4. Ejecutamos TU método real del servicio
		Pago resultado = pagoService.procesarPago(dto);

		// 5. Comprobamos con JUnit 5 que todo calce perfecto
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals("APROBADO", resultado.getEstado());
		assertEquals(2500.0, resultado.getMonto());
	}
}