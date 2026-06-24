package com.restaurante.ms_pedidos.client;

import com.restaurante.ms_pedidos.dto.response.ProductoCatalogoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo", url = "${catalogo.service.url}")
public interface CatalogoClient {

    @GetMapping("/api/productos/{id}")
    ProductoCatalogoDTO obtenerProductoPorId(@PathVariable("id") Long id);
}