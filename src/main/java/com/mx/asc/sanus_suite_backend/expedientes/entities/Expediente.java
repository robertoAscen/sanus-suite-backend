package com.mx.asc.sanus_suite_backend.expedientes.entities;

import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.util.entities.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "expedientes", uniqueConstraints = {
  @UniqueConstraint(name = "uk_expediente_tenant", columnNames = {"numero_expediente", "tenant_id"})
})
@Getter
@Setter
public class Expediente extends AuditableEntity {

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
}
