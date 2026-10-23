package com.mx.asc.sanus_suite_backend.facturacion.entities;

import com.mx.asc.sanus_suite_backend.facturacion.enums.EstadoFacturaEnum;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "facturas")
public class Factura {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tenant_id", nullable = false)
  private String tenantId;

  @Column(name = "paciente_id", nullable = false)
  private Long pacienteId;

  @Column(name = "paciente_nombre")
  private String pacienteNombre;

  @Column(name = "fecha_emision", nullable = false)
  private LocalDateTime fechaEmision = LocalDateTime.now();

  @Column(nullable = false)
  private Double subtotal;

  @Column(nullable = false)
  private Double impuestos;

  @Column(nullable = false)
  private Double total;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EstadoFacturaEnum estado = EstadoFacturaEnum.PENDIENTE;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "factura_id")
  private List<DetalleFactura> detalles;
}