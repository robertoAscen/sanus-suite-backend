package com.mx.asc.sanus_suite_backend.pacientes.dtos;

import lombok.Data;

@Data
public class PacienteDto {
  private Long id;
  private String nombre;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private String fechaNacimiento;
  private String sexo;
  private String curp;
  private String direccion;
  private String telefono;
  // Datos de Emergencia (NOM-004)
  private String contactoEmergenciaNombre;
  private String contactoEmergenciaTelefono;
  private String contactoEmergenciaParentesco;
  private String numeroExpediente;
}
