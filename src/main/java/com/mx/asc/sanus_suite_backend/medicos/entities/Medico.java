package com.mx.asc.sanus_suite_backend.medicos.entities;

import com.mx.asc.sanus_suite_backend.auth.entities.UserEntity;
import com.mx.asc.sanus_suite_backend.util.entities.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicos", uniqueConstraints = {
  @UniqueConstraint(name = "uk_medico_user", columnNames = {"user_id"}),
  @UniqueConstraint(name = "uk_medico_cedula", columnNames = {"cedula_profesional"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medico extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tenant_id", nullable = false)
  private String tenantId;

  // Relación 1:1 con la cuenta de acceso
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @Column(nullable = false, length = 100)
  private String nombre;

  @Column(name = "primer_apellido", nullable = false, length = 100)
  private String primerApellido;

  @Column(name = "segundo_apellido", length = 100)
  private String segundoApellido;

  @Column(name = "cedula_profesional", nullable = false, length = 50)
  private String cedulaProfesional;

  @Column(length = 100)
  private String especialidad;

  @Column(name = "institucion_titulo", length = 150)
  private String institucionTitulo;
}