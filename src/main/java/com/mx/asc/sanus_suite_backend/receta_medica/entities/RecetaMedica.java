package com.mx.asc.sanus_suite_backend.receta_medica.entities;

import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.util.entities.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recetas_medicas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaMedica extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tenant_id", nullable = false)
  private String tenantId;

  @Column(name = "expediente_id", nullable = false)
  private Long expedienteId;

  @Column(name = "nota_evolucion_id")
  private Long notaEvolucionId;

  @Column(name = "medico_id", nullable = false)
  private Long medicoId;

  @Column(name = "paciente_id", nullable = false)
  private Long pacienteId;

  @Column(name = "fecha_emision", nullable = false)
  private LocalDateTime fechaEmision;

  @Column(name = "observaciones_generales", columnDefinition = "TEXT")
  private String observacionesGenerales;

  @Column(name = "firmado")
  private boolean firmado;

  @Column(name = "fecha_firma")
  private LocalDateTime fechaFirma;

  @Column(name = "medico_nombre_snapshot")
  private String medicoNombreSnapshot;

  @Column(name = "medico_cedula_snapshot")
  private String medicoCedulaSnapshot;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "receta_id")
  @Builder.Default
  private List<RecetaDetalle> detalles = new ArrayList<>();

  public void asentarFirmaLegal(Medico medico) {
    if (medico == null) return;

    this.firmado = true;
    this.fechaFirma = LocalDateTime.now();

    this.medicoNombreSnapshot = String.format("%s %s %s",
      medico.getNombre() != null ? medico.getNombre() : "",
      medico.getPrimerApellido() != null ? medico.getPrimerApellido() : "",
      medico.getSegundoApellido() != null ? medico.getSegundoApellido() : "").trim();
    this.medicoCedulaSnapshot = medico.getCedulaProfesional();
  }
}
