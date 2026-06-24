package com.comidarapida.usuarios.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.comidarapida.usuarios.dto.UsuarioDTO;
import com.comidarapida.usuarios.service.UsuarioService;
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

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Gestión de Usuarios", description = "Endpoints para la administración del personal y clientes de pedidos.comidarapida.cl")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Crear un nuevo usuario", description = "Añade un nuevo usuario al sistema con sus credenciales cifradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO){
        log.info("Recibiendo petición para crear usuario con email: {}",usuarioDTO.getEmail());

        UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(usuarioDTO);

        // Añadimos el enlace HateOAS al usuario recién creado
        nuevoUsuario.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(nuevoUsuario.getId())).withSelfRel());
        nuevoUsuario.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios"));

        log.info("Usuario creado exitosamente con ID: {}",nuevoUsuario.getId());
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario del sistema permanentemente usando su ID.")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(description = "ID del usuario a eliminar") @PathVariable Long id){
        log.info("Recibiendo petición para eliminar usuario con ID: {}", id);
        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos los usuarios", description = "Retorna una lista completa de los usuarios registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista recuperada exitosamente")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(){
        log.info("Ejecutando petición para listar todos los usuarios");

        List<UsuarioDTO> usuarios = usuarioService.obtenerTodos();

        // Inyectando HateOAS a cada usuario de la lista
        for (UsuarioDTO usuario : usuarios) {
            usuario.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuario.getId())).withSelfRel());
            usuario.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios"));
        }

        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Buscar usuario por ID", description = "Retorna los detalles de un usuario específico usando su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO>obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario a buscar") @PathVariable Long id){
        log.info("Buscando usuario en la base de datos con ID: {}", id);
        return usuarioService.buscarPorId(id)
                .map(usuario -> {
                    // Inyectando HateOAS al usuario individual
                    usuario.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuario.getId())).withSelfRel());
                    usuario.add(linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios"));
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> {
                    log.warn("No se encontró ningún usuario con el ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }
}