package com.comidarapida.catalogo.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;


@FeignClient(name = "pagos-service", url = "http://localhost:8084/api/pagos")
public interface PagoClient {


    @PostMapping
    Object procesarPago(@RequestBody Map<String, Object> pagoRequest);

    @GetMapping
    List<Object> obtenerHistorialPagos();

}