package com.mx.asc.sanus_suite_backend.facturacion.dtos;

import lombok.Data;
import java.util.List;

@Data
public class FacturaRequestDto {
  private Long pacienteId;
  private String pacienteNombre;
  private Double subtotal;
  private Double impuestos;
  private Double total;
  private List<DetalleFacturaDto> detalles;
}
