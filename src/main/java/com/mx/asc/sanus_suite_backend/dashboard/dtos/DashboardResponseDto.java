package com.mx.asc.sanus_suite_backend.dashboard.dtos;

import lombok.Data;

@Data
public class DashboardResponseDto {
  private long totalPacientes;
  private long totalFacturas;
  private double ingresosTotales;
  private long productosStockBajo;
}