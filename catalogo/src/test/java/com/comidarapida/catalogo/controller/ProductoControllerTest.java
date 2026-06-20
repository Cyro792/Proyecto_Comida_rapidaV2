package com.comidarapida.catalogo.controller;

import com.comidarapida.catalogo.client.PagoClient;
import com.comidarapida.catalogo.dto.ProductoDTO;
import com.comidarapida.catalogo.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// Importación clave para apagar Flyway en esta prueba
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Aquí le decimos a la burbuja: "Carga el controlador, pero EXCLUYE Flyway"
@WebMvcTest(
        controllers = ProductoController.class,
        excludeAutoConfiguration = {FlywayAutoConfiguration.class}
)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Ya no necesitamos el DataSource falso

    @MockBean
    private ProductoService productoService;

    @MockBean
    private PagoClient pagoClient;

    @Test
    void listarProducto_DebeRetornarListaDeProductos_CuandoExisten() throws Exception {
        ProductoDTO productoFalso = new ProductoDTO(
                1L,
                "Hamburguesa",
                "Doble carne",
                5000.0
        );

        when(productoService.obtenerTodos()).thenReturn(List.of(productoFalso));

        mockMvc.perform(get("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Hamburguesa"))
                .andExpect(jsonPath("$[0].precio").value(5000.0));
    }
}