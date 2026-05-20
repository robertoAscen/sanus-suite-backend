package com.mx.asc.sanus_suite_backend.expedientes.entities;

import com.mx.asc.sanus_suite_backend.expedientes.enums.TipoNota;
import com.mx.asc.sanus_suite_backend.expedientes.util.ContenidoNotaConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "notas_medicas")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class NotaMedica {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "expediente_id", nullable = false) // Cambiado de paciente_id a expediente_id
  private Expediente expediente;

  @Column(nullable = false)
  private String medicoNombre;

  @Column(nullable = false)
  private String cedulaProfesional;

  private String diagnosticoPrincipal;

  @Enumerated(EnumType.STRING)
  private TipoNota tipoNota;

  @JdbcTypeCode(SqlTypes.JSON) // Importa de org.hibernate.annotations.JdbcTypeCode y org.hibernate.type.SqlTypes
  @Column(name = "contenido", columnDefinition = "jsonb")
  @Convert(converter = ContenidoNotaConverter.class)
  private Object contenido;

  @Column(name = "tenant_id", nullable = false)
  private String tenantId;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime fechaCreacion;

  @PrePersist
  protected void onCreate() {
    this.fechaCreacion = LocalDateTime.now();
  }

  @Column(nullable = false)
  private boolean firmado = false;

  // La NOM-024 exige saber quién modificó (Adendas)
  @LastModifiedBy
  private String modificadoPor;
}
