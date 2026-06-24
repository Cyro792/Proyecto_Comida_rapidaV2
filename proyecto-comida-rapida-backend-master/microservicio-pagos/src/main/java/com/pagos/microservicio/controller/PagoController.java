package com.pagos.microservicio.controller;

import com.pagos.microservicio.model.Pago;
import com.pagos.microservicio.repository.PagoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Controlador de Pagos", description = "Endpoints para procesar y consultar las transacciones de la app")
public class PagoController {

    // Inyectamos directamente tu repositorio real
    @Autowired
    private PagoRepository pagoRepository;

    //Endpoint para obtener todos los pagos usando el repositorio.

    @GetMapping
    @Operation(summary = "Obtener todos los pagos registrados", description = "Devuelve una lista completa con todos los pagos almacenados en la base de datos")
    public List<Pago> obtenerTodos() {
        // findAll() es el método nativo de JPA para listar todo
        return pagoRepository.findAll();
    }

    //Endpoint para registrar un nuevo pago usando el repositorio.

    @PostMapping
    @Operation(summary = "Registrar un nuevo pago", description = "Recibe los datos de una transacción (ordenId, monto, metodoPago) y la guarda con un estado inicial")
    public Pago crearPago(@RequestBody Pago pago) {
        if (pago.getEstado() == null) {
            pago.setEstado("PENDIENTE");
        }
        // save() es el método nativo de JPA para guardar registros
        return pagoRepository.save(pago);
    }
}