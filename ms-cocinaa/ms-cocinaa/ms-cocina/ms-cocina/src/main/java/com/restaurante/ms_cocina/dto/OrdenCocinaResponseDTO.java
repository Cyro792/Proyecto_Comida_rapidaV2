package com.restaurante.ms_cocina.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenCocinaResponseDTO {
    private Long id;
    private Long pedidoId;
    private String nombreCliente;
    private String estado;
    private LocalDateTime fechaRecepcion;
    private List<String> descripcionItems;
}