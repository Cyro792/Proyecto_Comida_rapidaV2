package com.restaurante.ms_pedidos.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenCocinaRequestDTO {

    private Long pedidoId;
    private String nombreCliente;
    private BigDecimal total;
    private List<ItemOrdenDTO> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemOrdenDTO {
        private Long productoId;
        private String nombreProducto;
        private Integer cantidad;
    }
}