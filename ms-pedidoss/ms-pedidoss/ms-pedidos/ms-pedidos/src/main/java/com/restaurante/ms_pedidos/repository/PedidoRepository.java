package com.restaurante.ms_pedidos.repository;

import com.restaurante.ms_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByNombreCliente(String nombreCliente);
    List<Pedido> findByEstado(String estado);
}