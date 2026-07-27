package com.mx.asc.sanus_suite_backend.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterDto {

  @NotBlank(message = "El nombre de usuario es obligatorio")
  @Email(message = "El formato del usuario debe ser un correo válido")
  private String username;

  @NotBlank(message = "La contraseña es obligatoria")
  private String password;

  @NotBlank(message = "El nombre completo es obligatorio")
  private String fullName;

  @NotNull(message = "El tenant ID es obligatorio")
  private String tenantId;
}
