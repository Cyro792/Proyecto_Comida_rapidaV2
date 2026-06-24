package com.comidarapida.usuarios.controller;

import com.comidarapida.usuarios.dto.UsuarioDTO;
import com.comidarapida.usuarios.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Excluimos las configuraciones de base de datos para que el test de controlador sea rápido y no falle
@WebMvcTest(controllers = UsuarioController.class,
        excludeAutoConfiguration = {DataSourceAutoConfiguration.class, FlywayAutoConfiguration.class})
class UsuariosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNombre("Bastian");
        usuarioDTO.setEmail("bastian@test.com");
        usuarioDTO.setPassword("pass123");
        usuarioDTO.setRol("ADMIN");
    }

    @Test
    @DisplayName("Debe retornar 201 y el usuario creado")
    void crearUsuarioTest() throws Exception {
        when(usuarioService.crearUsuario(any(UsuarioDTO.class))).thenReturn(usuarioDTO);

        String usuarioJson = "{\"nombre\":\"Bastian\", \"email\":\"bastian@test.com\", \"password\":\"pass123\", \"rol\":\"ADMIN\"}";

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Bastian"))
                .andExpect(jsonPath("$.email").value("bastian@test.com"));
    }

    @Test
    @DisplayName("Debe retornar 200 y una lista de usuarios")
    void listarUsuariosTest() throws Exception {
        when(usuarioService.obtenerTodos()).thenReturn(Arrays.asList(usuarioDTO));

        mockMvc.perform(get("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Bastian"))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Debe retornar 200 y el usuario si existe por ID")
    void obtenerUsuarioPorIdTest() throws Exception {
        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(usuarioDTO));

        mockMvc.perform(get("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("bastian@test.com"));
    }

    @Test
    @DisplayName("Debe retornar 404 si el usuario no existe")
    void obtenerUsuarioPorIdNoEncontradoTest() throws Exception {
        when(usuarioService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe retornar 204 al eliminar un usuario")
    void eliminarUsuarioTest() throws Exception {
        doNothing().when(usuarioService).eliminarUsuario(1L);

        mockMvc.perform(delete("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debe retornar 200 al generar datos masivos")
    void generarDatosMasivosTest() throws Exception {
        doNothing().when(usuarioService).generarDatosFalsos(10);

        mockMvc.perform(post("/api/usuarios/generar-datos/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("¡Éxito! Se generaron 10 usuarios aleatorios en la base de datos."));
    }
}