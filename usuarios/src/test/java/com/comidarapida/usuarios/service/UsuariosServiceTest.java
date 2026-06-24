package com.comidarapida.usuarios.service;

import com.comidarapida.usuarios.dto.UsuarioDTO;
import com.comidarapida.usuarios.model.Usuario;
import com.comidarapida.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuariosServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioMock;
    private UsuarioDTO usuarioDTOMock;

    @BeforeEach
    void setUp() {
        // Datos simulados para usar en cada prueba
        usuarioMock = new Usuario(1L, "Bastian", "bastian@test.com", "pass123", "ADMIN");
        usuarioDTOMock = new UsuarioDTO();
        usuarioDTOMock.setId(1L);
        usuarioDTOMock.setNombre("Bastian");
        usuarioDTOMock.setEmail("bastian@test.com");
        usuarioDTOMock.setPassword("pass123");
        usuarioDTOMock.setRol("ADMIN");
    }

    @Test
    @DisplayName("Debe crear un usuario y retornar su DTO")
    void crearUsuarioTest() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

        UsuarioDTO resultado = usuarioService.crearUsuario(usuarioDTOMock);

        assertNotNull(resultado);
        assertEquals("Bastian", resultado.getNombre());
        assertEquals("bastian@test.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe eliminar un usuario llamando al repositorio")
    void eliminarUsuarioTest() {
        usuarioService.eliminarUsuario(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debe listar todos los usuarios convertidos a DTO")
    void obtenerTodosTest() {
        Usuario usuarioMock2 = new Usuario(2L, "Leo", "leo@test.com", "pass456", "USER");
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuarioMock, usuarioMock2));

        List<UsuarioDTO> resultado = usuarioService.obtenerTodos();

        assertEquals(2, resultado.size());
        assertEquals("Leo", resultado.get(1).getNombre());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar un usuario por ID")
    void buscarPorIdTest() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));

        Optional<UsuarioDTO> resultado = usuarioService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Bastian", resultado.get().getNombre());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe buscar un usuario por Email")
    void buscarPorEmailTest() {
        when(usuarioRepository.findByEmail("bastian@test.com")).thenReturn(Optional.of(usuarioMock));

        Optional<Usuario> resultado = usuarioService.buscarPorEmail("bastian@test.com");

        assertTrue(resultado.isPresent());
        assertEquals("ADMIN", resultado.get().getRol());
        verify(usuarioRepository, times(1)).findByEmail("bastian@test.com");
    }

    @Test
    @DisplayName("Debe generar datos falsos masivos con DataFaker")
    void generarDatosFalsosTest() {
        usuarioService.generarDatosFalsos(10);

        // Verifica que se guardó una lista completa de usuarios
        verify(usuarioRepository, times(1)).saveAll(anyList());
    }
}