package com.restaurante.ms_cocina.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCocinaRequestDTO {

    @NotNull(message = "El id del pedido es obligatorio")
    private Long pedidoId;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    private BigDecimal total;

    private List<ItemOrdenDTO> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemOrdenDTO {
        private Long productoId;
        private String nombreProducto;
        private Integer cantidad;
    }
}