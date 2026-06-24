package com.restaurante.ms_pedidos.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    @NotEmpty(message = "El pedido debe tener al menos un item")
    //@Valid
    private List<ItemPedidoRequestDTO> items;
}