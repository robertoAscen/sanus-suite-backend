package com.mx.asc.sanus_suite_backend.nota_evolucion.entities;

import com.mx.asc.sanus_suite_backend.util.entities.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "nota_evolucion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotaEvolucion extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "expediente_id", nullable = false)
  private Long expedienteId;

  @Column(name = "paciente_id", nullable = false)
  private Long pacienteId;

  @Column(name = "medico_id", nullable = false)
  private Long medicoId;

  @Column(name = "historia_clinica_id")
  private Long historiaClinicaId;

  @Column(name = "tenant_id", nullable = false)
  private String tenantId;

  @Column(name = "fecha_consulta", nullable = false)
  private LocalDateTime fechaConsulta;

  // --- Estructura SOAP ---
  @Column(columnDefinition = "TEXT", nullable = false)
  private String subjetivo;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String objetivo;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String analisis;

  @Column(name = "plan_tratamiento", columnDefinition = "TEXT", nullable = false)
  private String planTratamiento;

  // --- Signos Vitales ---
  @Column(name = "presion_arterial", length = 20)
  private String presionArterial;

  @Column(name = "frecuencia_cardiaca")
  private Integer frecuenciaCardiaca;

  @Column(name = "frecuencia_respiratoria")
  private Integer frecuenciaRespiratoria;

  private BigDecimal temperatura;

  private BigDecimal peso;

  private BigDecimal talla;

  private BigDecimal imc;

  @Column(name = "saturacion_oxigeno")
  private Integer saturacionOxigeno;

  @Column(nullable = false)
  private boolean firmado = false;

  @Column(name = "fecha_firma")
  private LocalDateTime fechaFirma;

  @Column(name = "firmado_por_medico_id")
  private Long firmadoPorMedicoId;

  @Column(name = "medico_nombre_snapshot")
  private String medicoNombreSnapshot;

  @Column(name = "medico_cedula_snapshot")
  private String medicoCedulaSnapshot;
}
