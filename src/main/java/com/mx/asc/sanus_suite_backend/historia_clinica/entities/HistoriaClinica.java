package com.mx.asc.sanus_suite_backend.historia_clinica.entities;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.util.entities.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

  @Column(columnDefinition = "TEXT", nullable = false)
  private String padecimientoActual;

  // Secciones embebidas para mantener el código ordenado en objetos Java
  @Embedded
  private AntecedentesHeredofamiliares antecedentesHeredofamiliares;

  @Embedded
  private AntecedentesPersonalesPatologicos antecedentesPersonalesPatologicos;

  @Embedded
  private AntecedentesPersonalesNoPatologicos antecedentesPersonalesNoPatologicos;

  // Los antecedentes gineco-obstétricos podrían ser otra entidad o embebido opcional
  @Embedded
  private AntecedentesGinecoObstetricos antecedentesGinecoObstetricos;

  // --- NUEVOS CAMPOS DE SEGURIDAD CLÍNICA ---
  @Column(nullable = false)
  private boolean firmado = false;

  @Column(name = "fecha_firma")
  private LocalDateTime fechaFirma;

  @Column(name = "firmado_por_medico_id")
  private Long firmadoPorMedicoId; // ID del médico que plasmó su firma digital
}