package com.restaurante.ms_cocina.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes_cocina")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenCocina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pedidoId;

    @Column(nullable = false, length = 100)
    private String nombreCliente;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(nullable = false)
    private LocalDateTime fechaRecepcion;

    @Column(columnDefinition = "TEXT")
    private String descripcionItems;
}