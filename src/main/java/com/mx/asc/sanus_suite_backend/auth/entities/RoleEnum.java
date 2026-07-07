package com.mx.asc.sanus_suite_backend.auth.entities;

public enum RoleEnum {
  ROLE_SUPER_ADMIN,    // Soporte / Dueño del SaaS (Tú)
  ROLE_ADMIN,          // Administrador / Dueño de la clínica
  ROLE_DOCTOR,         // Médico / Especialista
  ROLE_RECEPTIONIST    // Recepción / Asistente
}