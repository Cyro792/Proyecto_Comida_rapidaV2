package com.restaurante.ms_cocina.service;

import com.restaurante.ms_cocina.dto.EstadoOrdenRequestDTO;
import com.restaurante.ms_cocina.dto.OrdenCocinaRequestDTO;
import com.restaurante.ms_cocina.dto.OrdenCocinaResponseDTO;
import java.util.List;

public interface CocinaService {
    OrdenCocinaResponseDTO crearOrden(OrdenCocinaRequestDTO dto);
    List<OrdenCocinaResponseDTO> listarPorEstado(String estado);
    OrdenCocinaResponseDTO buscarPorId(Long id);
    OrdenCocinaResponseDTO actualizarEstado(Long id, EstadoOrdenRequestDTO dto);
    List<OrdenCocinaResponseDTO> listarTodas();
}
