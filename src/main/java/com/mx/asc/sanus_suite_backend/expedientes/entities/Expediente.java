package com.mx.asc.sanus_suite_backend.expedientes.entities;

import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "expedientes", uniqueConstraints = {
  @UniqueConstraint(name = "uk_expediente_tenant", columnNames = {"numero_expediente", "tenant_id"})
})
@EntityListeners(AuditingEntityListener.class) // Agregamos auditoría
@Getter
@Setter
public class Expediente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "paciente_id", nullable = false)
  private Paciente paciente;

  @Column(unique = true, nullable = false)
  private String numeroExpediente;

  @Column(name = "tenant_id", nullable = false)
  private String tenantId;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime fechaCreacion;

  @PrePersist
  protected void onCreate() {
    this.fechaCreacion = LocalDateTime.now();
  }
}
