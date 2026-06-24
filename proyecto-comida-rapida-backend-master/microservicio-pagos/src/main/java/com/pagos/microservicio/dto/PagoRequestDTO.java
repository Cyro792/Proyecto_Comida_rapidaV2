package com.pagos.microservicio.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoRequestDTO {

    @NotNull(message = "El ID en la orden es obligatorio")
    private Long ordenId;

    @NotNull(message = "El monto no puede ser nulo!!")
    @Min(value = 1, message = "El monto mínimo a pagar debe ser mayor a 0!!")
    private Double monto;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;
}