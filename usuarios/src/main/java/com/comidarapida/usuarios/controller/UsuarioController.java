package com.comidarapida.usuarios.controller;

import com.comidarapida.usuarios.dto.UsuarioDTO;
import com.comidarapida.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO){
        log.info("Recibiendo petición para crear usuario con email: {}",usuarioDTO.getEmail());

        // El Controlador no transforma nada, solo delega al Servicio
        UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(usuarioDTO);

        log.info("Usuario creado exitosamente con ID: {}",nuevoUsuario.getId());
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id){
        log.info("Recibiendo petición para eliminar usuario con ID: {}", id);
        usuarioService.eliminarUsuario(id);

        // Retornamos 204 No Content, que es el estándar profesional cuando se elimina algo con éxito
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(){
        log.info("Ejecutando petición para listar todos los usuarios");
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO>obtenerUsuarioPorId(@PathVariable Long id){
        log.info("Buscando usuario en la base de datos con ID: {}", id);
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("No se encontró ningún usuario con el ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }
}






