package com.Microservicio.Inventario.MicroservicioInventario.repository;

import com.Microservicio.Inventario.MicroservicioInventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}