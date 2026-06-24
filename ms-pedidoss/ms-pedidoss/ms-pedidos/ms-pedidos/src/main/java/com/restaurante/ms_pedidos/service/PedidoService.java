package com.restaurante.ms_pedidos.service;

import com.restaurante.ms_pedidos.dto.request.PedidoRequestDTO;
import com.restaurante.ms_pedidos.dto.response.PedidoResponseDTO;
import java.util.List;

public interface PedidoService {
    PedidoResponseDTO crearPedido(PedidoRequestDTO dto);
    PedidoResponseDTO buscarPorId(Long id);
    List<PedidoResponseDTO> listarTodos();
}