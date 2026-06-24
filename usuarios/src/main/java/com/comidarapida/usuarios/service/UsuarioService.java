package com.comidarapida.usuarios.service;

import com.comidarapida.usuarios.dto.UsuarioDTO;
import com.comidarapida.usuarios.model.Usuario;
import com.comidarapida.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker; // <-- Inyección V2
import org.springframework.stereotype.Service;

import java.util.ArrayList; // <-- Inyección V2
import java.util.List;
import java.util.Locale; // <-- Inyección V2
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO){
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(usuarioDTO.getNombre());
        nuevoUsuario.setEmail(usuarioDTO.getEmail());
        nuevoUsuario.setRol(usuarioDTO.getRol());

        nuevoUsuario.setPassword(usuarioDTO.getPassword());

        Usuario guardado = usuarioRepository.save(nuevoUsuario);
        return convertirADto(guardado);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertirADto);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // --- INICIO INYECCIÓN DATAFAKER V2 ---
    public void generarDatosFalsos(int cantidad) {
        Faker faker = new Faker(new Locale("es"));
        List<Usuario> nuevosUsuarios = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().fullName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setPassword(faker.internet().password(8, 16, true, true));
            usuario.setRol(faker.bool().bool() ? "USER" : "ADMIN");

            nuevosUsuarios.add(usuario);
        }

        usuarioRepository.saveAll(nuevosUsuarios);
    }
    // --- FIN INYECCIÓN DATAFAKER V2 ---

    private UsuarioDTO convertirADto(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());
        return dto;
    }
}