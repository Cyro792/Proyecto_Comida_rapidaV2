package com.restaurante.ms_cocina.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoOrdenRequestDTO {

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}