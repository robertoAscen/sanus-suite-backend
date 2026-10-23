package com.mx.asc.sanus_suite_backend.facturacion.dtos;

import lombok.Data;

@Data
public class DetalleFacturaDto {
  private Long productoId;
  private String descripcion;
  private Integer cantidad;
  private Double precioUnitario;
  private Double subtotal;
}