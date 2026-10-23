package com.mx.asc.sanus_suite_backend.facturacion.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalles_factura")
public class DetalleFactura {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "producto_id")
  private Long productoId;

  @Column(nullable = false)
  private String descripcion;

  @Column(nullable = false)
  private Integer cantidad;

  @Column(name = "precio_unitario", nullable = false)
  private Double precioUnitario;

  @Column(nullable = false)
  private Double subtotal;
}