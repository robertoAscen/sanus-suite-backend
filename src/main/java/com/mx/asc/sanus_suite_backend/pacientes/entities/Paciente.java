package com.mx.asc.sanus_suite_backend.pacientes.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pacientes", uniqueConstraints = {
  @UniqueConstraint(name = "uk_paciente_tenant", columnNames = {"curp", "tenant_id"})
})
@SQLDelete(sql = "UPDATE pacientes SET activo = false WHERE id = ?")
@Where(clause = "activo = true")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tenant_id", nullable = false)
  private String tenantId;

  @NotBlank(message = "El nombre es obligatorio")
  @Column(nullable = false)
  private String nombre;

  @NotBlank(message = "El apellido paterno es obligatorio")
  @Column(nullable = false)
  private String apellidoPaterno;

  @NotBlank(message = "El apellido materno es obligatorio")
  @Column(nullable = false)
  private String apellidoMaterno;

  @Column(nullable = false)
  private String fechaNacimiento;

  @Column(nullable = false)
  private String sexo;

  @NotBlank(message = "La CURP es obligatoria")
  @Pattern(regexp = "^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[A-Z0-9]{2}$", message = "Formato de CURP inválido")
  @Column(nullable = false)
  private String curp;

  private String direccion;
  private String telefono;
  private String contactoEmergenciaNombre;
  private String contactoEmergenciaTelefono;
  private String contactoEmergenciaParentesco;

  @CreatedBy
  private String creadoPor;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime fechaCreacion;

  @Column(nullable = false)
  private boolean activo = true;

  @PrePersist
  protected void onCreate() {
    this.fechaCreacion = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "Paciente{" +
      "nombre='" + nombre + '\'' +
      ", apellidoPaterno='" + apellidoPaterno + '\'' +
      ", apellidoMaterno='" + apellidoMaterno + '\'' +
      '}';
  }
}