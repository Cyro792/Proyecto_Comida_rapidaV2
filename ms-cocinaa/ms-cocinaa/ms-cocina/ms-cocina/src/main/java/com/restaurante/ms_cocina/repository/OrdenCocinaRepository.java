package com.restaurante.ms_cocina.repository;

import com.restaurante.ms_cocina.model.OrdenCocina;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrdenCocinaRepository extends JpaRepository<OrdenCocina, Long> {

    List<OrdenCocina> findByEstado(String estado);
    Optional<OrdenCocina> findByPedidoId(Long pedidoId);
}