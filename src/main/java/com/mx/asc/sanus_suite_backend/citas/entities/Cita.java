package com.mx.asc.sanus_suite_backend.citas.entities;

import com.mx.asc.sanus_suite_backend.citas.enums.EstadoCitaEnum;
import com.mx.asc.sanus_suite_backend.util.entities.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "citas_medicas", indexes = {
  @Index(name = "idx_cita_tenant_fecha", columnList = "tenant_id, fecha_hora_inicio"),
  @Index(name = "idx_cita_medico", columnList = "medico_id, tenant_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tenant_id", nullable = false)
  private String tenantId;

  @Column(name = "medico_id", nullable = false)
  private Long medicoId;

  @Column(name = "paciente_id", nullable = false)
  private Long pacienteId;

  @Column(name = "expediente_id")
  private Long expedienteId;

  @Column(name = "fecha_hora_inicio", nullable = false)
  private LocalDateTime fechaHoraInicio;

  @Column(name = "fecha_hora_fin", nullable = false)
  private LocalDateTime fechaHoraFin;

  @Column(name = "motivo_consulta", length = 500)
  private String motivoConsulta;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private EstadoCitaEnum estado;

  @Column(columnDefinition = "TEXT")
  private String observaciones;

  @Column(name = "nota_evolucion_id")
  private Long notaEvolucionId;
}