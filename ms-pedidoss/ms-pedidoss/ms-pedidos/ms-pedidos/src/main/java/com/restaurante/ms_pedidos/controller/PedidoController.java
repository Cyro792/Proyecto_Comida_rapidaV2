package com.restaurante.ms_pedidos.controller;

import com.restaurante.ms_pedidos.dto.request.PedidoRequestDTO;
import com.restaurante.ms_pedidos.dto.response.PedidoResponseDTO;
import com.restaurante.ms_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Slf4j
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crear(
            @Valid @RequestBody PedidoRequestDTO dto) {
        log.info("POST /api/pedidos - cliente: {}", dto.getNombreCliente());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoService.crearPedido(dto));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar() {
        log.info("GET /api/pedidos");
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/pedidos/{}", id);
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }
}