package com.mx.asc.sanus_suite_backend.dashboard.services;

import com.mx.asc.sanus_suite_backend.dashboard.dtos.DashboardResponseDto;
import com.mx.asc.sanus_suite_backend.handlers.PacienteHandler;
import com.mx.asc.sanus_suite_backend.inventario.repositories.ProductoRepository;
import com.mx.asc.sanus_suite_backend.pacientes.dtos.PacienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

  @Autowired
  private PacienteHandler pacienteHandler;

  // TODO: Descomentar cuando hagas merge del módulo de facturas desde tu equipo de escritorio
  // @Autowired
  // private FacturaRepository facturaRepository;

  @Autowired
  private ProductoRepository productoRepository;

  public DashboardResponseDto obtenerResumen(String tenantId) {
    // 1. Obtener total de pacientes a través del Handler
    long totalPacientes = 0;
    try {
      List<PacienteDto> listaPacientes = pacienteHandler.listaPacientes(tenantId);
      if (listaPacientes != null) {
        totalPacientes = listaPacientes.size();
      }
    } catch (Exception e) {
      totalPacientes = 0;
    }

    // 2. Facturas (Valores temporales en 0 hasta mergear con tu laptop/escritorio)
    long totalFacturas = 0;
    double ingresosTotales = 0.0;

    // 3. Productos con stock bajo
    long stockBajo = 0;
    try {
      var productosList = productoRepository.findByTenantId(tenantId);
      if (productosList != null) {
        stockBajo = productosList.stream()
          .filter(p -> p.getStockActual() != null && p.getStockMinimo() != null && p.getStockActual() <= p.getStockMinimo())
          .count();
      }
    } catch (Exception e) {
      stockBajo = 0;
    }

    // 4. Construcción y retorno del DTO
    DashboardResponseDto dto = new DashboardResponseDto();
    dto.setTotalPacientes(totalPacientes);
    dto.setTotalFacturas(totalFacturas);
    dto.setIngresosTotales(ingresosTotales);
    dto.setProductosStockBajo(stockBajo);

    return dto;
  }
}