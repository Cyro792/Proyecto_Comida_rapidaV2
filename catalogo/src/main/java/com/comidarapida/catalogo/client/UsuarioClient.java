package com.comidarapida.catalogo.client;

import com.comidarapida.catalogo.dto.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuarios-service",url = "http://localhost:8081/api/usuarios")
public interface UsuarioClient {

    @GetMapping("/{id}")
    UsuarioResponseDTO obtenerUsuarioPorId(@PathVariable("id")Long id);
}
