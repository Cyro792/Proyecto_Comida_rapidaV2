package com.pagos.microservicio.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "pagos")
@Data
@EqualsAndHashCode(callSuper = false) // Evita conflictos con los métodos de la clase padre
public class Pago extends RepresentationModel<Pago> { // Heredamos para poder usar enlaces

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ordenId;
    private Double monto;
    private String metodoPago;
    private String estado;
}