package com.comidarapida.catalogo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false) // Evita advertencias de Lombok al heredar de RepresentationModel
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO extends RepresentationModel<ProductoDTO> {

    private Long idProducto;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio no puede ser negativo ni cero")
    private Double precio;
}