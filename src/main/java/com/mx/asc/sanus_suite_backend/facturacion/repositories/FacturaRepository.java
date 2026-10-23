package com.mx.asc.sanus_suite_backend.facturacion.repositories;

import com.mx.asc.sanus_suite_backend.facturacion.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
  List<Factura> findByTenantId(String tenantId);
}
