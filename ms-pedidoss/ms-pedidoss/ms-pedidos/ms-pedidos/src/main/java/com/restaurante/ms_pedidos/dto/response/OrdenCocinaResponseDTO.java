package com.restaurante.ms_pedidos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCocinaResponseDTO {
    private Long id;
    private Long pedidoId;
    private String nombreCliente;
    private String estado;
    private LocalDateTime fechaRecepcion;
    private List<String> descripcionItems;
}
