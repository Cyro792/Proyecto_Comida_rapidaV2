package com.comidarapida.catalogo.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


@FeignClient(name = "inventario-service", url = "http://localhost:8083/api/productos")
public interface InventarioClient {


    @GetMapping
    ResponseEntity<List<Object>> obtenerProductosDelInventario();
}