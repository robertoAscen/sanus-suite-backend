package com.mx.asc.sanus_suite_backend.inventario.dtos;

import lombok.Data;

@Data
public class ProductoRequestDto {
  private String nombre;
  private String codigo;
  private String descripcion;
  private Double precioCompra;
  private Double precioVenta;
  private Integer stockActual;
  private Integer stockMinimo;
}