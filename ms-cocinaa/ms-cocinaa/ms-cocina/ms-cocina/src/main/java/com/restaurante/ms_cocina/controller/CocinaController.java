package com.restaurante.ms_cocina.controller;

import com.restaurante.ms_cocina.dto.EstadoOrdenRequestDTO;
import com.restaurante.ms_cocina.dto.OrdenCocinaRequestDTO;
import com.restaurante.ms_cocina.dto.OrdenCocinaResponseDTO;
import com.restaurante.ms_cocina.service.CocinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cocina")
@RequiredArgsConstructor
@Slf4j
public class CocinaController {

    private final CocinaService cocinaService;

    @PostMapping
    public ResponseEntity<OrdenCocinaResponseDTO> crearOrden(
            @Valid @RequestBody OrdenCocinaRequestDTO dto) {
        log.info("POST /api/cocina - pedidoId: {}", dto.getPedidoId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cocinaService.crearOrden(dto));
    }

    @GetMapping
    public ResponseEntity<List<OrdenCocinaResponseDTO>> listarTodas() {
        log.info("GET /api/cocina");
        return ResponseEntity.ok(cocinaService.listarTodas());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<OrdenCocinaResponseDTO>> listarPorEstado(
            @PathVariable String estado) {
        log.info("GET /api/cocina/estado/{}", estado);
        return ResponseEntity.ok(cocinaService.listarPorEstado(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenCocinaResponseDTO> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/cocina/{}", id);
        return ResponseEntity.ok(cocinaService.buscarPorId(id));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<OrdenCocinaResponseDTO> actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody EstadoOrdenRequestDTO dto) {
        log.info("PUT /api/cocina/{}/estado - {}", id, dto.getEstado());
        return ResponseEntity.ok(cocinaService.actualizarEstado(id, dto));
    }
}