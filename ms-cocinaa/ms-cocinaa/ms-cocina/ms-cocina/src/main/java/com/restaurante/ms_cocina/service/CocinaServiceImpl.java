package com.restaurante.ms_cocina.service;

import com.restaurante.ms_cocina.dto.EstadoOrdenRequestDTO;
import com.restaurante.ms_cocina.dto.OrdenCocinaRequestDTO;
import com.restaurante.ms_cocina.dto.OrdenCocinaResponseDTO;
import com.restaurante.ms_cocina.model.OrdenCocina;
import com.restaurante.ms_cocina.repository.OrdenCocinaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CocinaServiceImpl implements CocinaService {

    private final OrdenCocinaRepository ordenCocinaRepository;

    private static final List<String> ESTADOS_VALIDOS =
            Arrays.asList("EN_PREPARACION", "LISTO", "ENTREGADO");

    @Override
    public OrdenCocinaResponseDTO crearOrden(OrdenCocinaRequestDTO dto) {
        log.info("Recibiendo orden desde ms-pedidos - pedidoId: {}", dto.getPedidoId());

        String descripcion = "";
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            descripcion = dto.getItems().stream()
                    .map(i -> i.getCantidad() + "x " + i.getNombreProducto())
                    .collect(Collectors.joining(", "));
        }

        OrdenCocina orden = OrdenCocina.builder()
                .pedidoId(dto.getPedidoId())
                .nombreCliente(dto.getNombreCliente())
                .estado("EN_PREPARACION")
                .fechaRecepcion(LocalDateTime.now())
                .descripcionItems(descripcion)
                .build();

        OrdenCocina guardada = ordenCocinaRepository.save(orden);
        log.info("Orden {} creada en cocina con estado EN_PREPARACION",
                dto.getPedidoId());

        return mapToResponse(guardada);
    }

    @Override
    public List<OrdenCocinaResponseDTO> listarTodas() {
        log.info("Listando todas las órdenes de cocina");
        return ordenCocinaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdenCocinaResponseDTO> listarPorEstado(String estado) {
        log.info("Listando órdenes con estado: {}", estado);
        return ordenCocinaRepository.findByEstado(estado)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrdenCocinaResponseDTO buscarPorId(Long id) {
        log.info("Buscando orden cocina id: {}", id);

        OrdenCocina orden = ordenCocinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada: " + id));

        return mapToResponse(orden);
    }

    @Override
    public OrdenCocinaResponseDTO actualizarEstado(Long id, EstadoOrdenRequestDTO dto) {
        log.info("Actualizando estado de orden {} a {}", id, dto.getEstado());

        if (!ESTADOS_VALIDOS.contains(dto.getEstado())) {
            log.error("Estado inválido recibido: {}", dto.getEstado());
            throw new RuntimeException(
                    "Estado inválido: " + dto.getEstado()
                            + ". Estados válidos: " + ESTADOS_VALIDOS);
        }

        OrdenCocina orden = ordenCocinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada: " + id));

        orden.setEstado(dto.getEstado());
        OrdenCocina actualizada = ordenCocinaRepository.save(orden);
        log.info("Orden {} actualizada a estado {}", id, dto.getEstado());

        return mapToResponse(actualizada);
    }

    private OrdenCocinaResponseDTO mapToResponse(OrdenCocina o) {
        List<String> items = (o.getDescripcionItems() != null
                && !o.getDescripcionItems().isBlank())
                ? Arrays.asList(o.getDescripcionItems().split(", "))
                : List.of();

        return OrdenCocinaResponseDTO.builder()
                .id(o.getId())
                .pedidoId(o.getPedidoId())
                .nombreCliente(o.getNombreCliente())
                .estado(o.getEstado())
                .fechaRecepcion(o.getFechaRecepcion())
                .descripcionItems(items)
                .build();
    }
}