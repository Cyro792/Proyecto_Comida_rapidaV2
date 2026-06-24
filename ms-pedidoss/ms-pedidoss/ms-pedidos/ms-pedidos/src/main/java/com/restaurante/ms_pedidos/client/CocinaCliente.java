package com.restaurante.ms_pedidos.client;

import com.restaurante.ms_pedidos.dto.response.OrdenCocinaResponseDTO;
import com.restaurante.ms_pedidos.dto.request.OrdenCocinaRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-cocina", url = "${cocina.service.url}")
public interface CocinaCliente {

    @PostMapping("/api/cocina")
    OrdenCocinaResponseDTO enviarOrdenACocina(@RequestBody OrdenCocinaRequestDTO dto);
}