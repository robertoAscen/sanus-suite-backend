package com.mx.asc.sanus_suite_backend.inventario.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "productos")
public class Producto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tenant_id", nullable = false)
  private String tenantId;

  @Column(nullable = false)
  private String nombre;

  @Column(unique = true)
  private String codigo;

  private String descripcion;

  @Column(name = "precio_compra")
  private Double precioCompra;

  @Column(name = "precio_venta", nullable = false)
  private Double precioVenta;

  @Column(name = "stock_actual", nullable = false)
  private Integer stockActual;

  @Column(name = "stock_minimo")
  private Integer stockMinimo;

  @Column(name = "fecha_creacion")
  private LocalDateTime fechaCreacion = LocalDateTime.now();
}