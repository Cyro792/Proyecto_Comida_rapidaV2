package com.restaurante.ms_pedidos.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponseDTO {
    private Long id;
    private String nombreCliente;
    private String estado;
    private BigDecimal total;
    private LocalDateTime fechaCreacion;
    private List<ItemPedidoResponseDTO> items;
}