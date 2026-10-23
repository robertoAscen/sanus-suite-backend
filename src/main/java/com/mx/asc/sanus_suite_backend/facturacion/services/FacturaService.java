package com.mx.asc.sanus_suite_backend.facturacion.services;

import com.mx.asc.sanus_suite_backend.facturacion.dtos.FacturaRequestDto;
import com.mx.asc.sanus_suite_backend.facturacion.entities.DetalleFactura;
import com.mx.asc.sanus_suite_backend.facturacion.entities.Factura;
import com.mx.asc.sanus_suite_backend.facturacion.repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaService {

  @Autowired
  private FacturaRepository facturaRepository;

  public List<Factura> listarFacturas(String tenantId) {
    return facturaRepository.findByTenantId(tenantId);
  }

  public Factura crearFactura(String tenantId, FacturaRequestDto dto) {
    Factura factura = new Factura();
    factura.setTenantId(tenantId);
    factura.setPacienteId(dto.getPacienteId());
    factura.setPacienteNombre(dto.getPacienteNombre());
    factura.setSubtotal(dto.getSubtotal());
    factura.setImpuestos(dto.getImpuestos());
    factura.setTotal(dto.getTotal());

    List<DetalleFactura> detalles = dto.getDetalles().stream().map(d -> {
      DetalleFactura det = new DetalleFactura();
      det.setProductoId(d.getProductoId());
      det.setDescripcion(d.getDescripcion());
      det.setCantidad(d.getCantidad());
      det.setPrecioUnitario(d.getPrecioUnitario());
      det.setSubtotal(d.getSubtotal());
      return det;
    }).collect(Collectors.toList());

    factura.setDetalles(detalles);
    return facturaRepository.save(factura);
  }
}
