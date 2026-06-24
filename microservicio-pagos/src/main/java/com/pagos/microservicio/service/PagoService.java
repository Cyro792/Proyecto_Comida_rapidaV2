package com.pagos.microservicio.service;

import com.pagos.microservicio.dto.PagoRequestDTO;
import com.pagos.microservicio.model.Pago;
import com.pagos.microservicio.repository.PagoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public Pago procesarPago(PagoRequestDTO dto) {
        // Registramos un log de trazabilidad al iniciar la operación
        log.info("Iniciando procesamiento de pago para la orden ID: {}", dto.getOrdenId());

        // 1. Instanciamos la entidad de persistencia real
        Pago pago = new Pago();

        // 2. Mapeamos los datos limpios y validados del DTO a la entidad
        pago.setOrdenId(dto.getOrdenId());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());

        // 3. Establecemos el estado de la transacción simulada
        pago.setEstado("APROBADO");

        // 4. Guardamos en la base de datos MySQL (Laragon)
        Pago pagoGuardado = pagoRepository.save(pago);

        // Registramos un log indicando que todo salió bien
        log.info("Pago procesado exitosamente y guardado con ID de transaccion: {}", pagoGuardado.getId());

        return pagoGuardado;
    }
}