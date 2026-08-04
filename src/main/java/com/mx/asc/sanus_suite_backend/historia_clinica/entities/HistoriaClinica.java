package com.mx.asc.sanus_suite_backend.historia_clinica.entities;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.util.entities.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "historias_clinicas", uniqueConstraints = {
  @UniqueConstraint(name = "uk_historia_expediente", columnNames = {"expediente_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaClinica extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tenant_id", nullable = false)
  private String tenantId;

  // Relación OneToOne con Expediente (El expediente es el padre)
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "expediente_id", nullable = false)
  private Expediente expediente;

  @Column(name = "motivo_consulta", columnDefinition = "TEXT")
  private String motivoConsulta;

  @Column(name = "padecimiento_actual", columnDefinition = "TEXT", nullable = false)
  private String padecimientoActual;

  @Column(name = "antecedentes_heredofamiliares", columnDefinition = "TEXT")
  private String antecedentesHeredofamiliares;

  @Column(name = "antecedentes_patologicos", columnDefinition = "TEXT")
  private String antecedentesPatologicos;

  @Column(name = "antecedentes_no_patologicos", columnDefinition = "TEXT")
  private String antecedentesNoPatologicos;

  @Column(name = "interrogatorio_aparatos_sistemas", columnDefinition = "TEXT")
  private String interrogatorioAparatosSistemas;

  @Column(name = "exploracion_fisica", columnDefinition = "TEXT")
  private String exploracionFisica;

  @Column(name = "diagnostico", columnDefinition = "TEXT")
  private String diagnostico;

  @Column(name = "plan_tratamiento", columnDefinition = "TEXT")
  private String planTratamiento;

  // --- CAMPOS DE SEGURIDAD CLÍNICA / FIRMA ---

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