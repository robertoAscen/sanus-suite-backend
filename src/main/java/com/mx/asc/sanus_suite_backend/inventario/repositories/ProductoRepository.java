package com.mx.asc.sanus_suite_backend.inventario.repositories;

import com.mx.asc.sanus_suite_backend.inventario.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
  List<Producto> findByTenantId(String tenantId);
}
