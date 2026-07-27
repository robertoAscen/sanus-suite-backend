package com.mx.asc.sanus_suite_backend.auth.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

  @NotBlank(message = "El nombre de usuario o correo es obligatorio.")
  private String username;

  @NotBlank(message = "La contraseña es obligatoria.")
  private String password;
}
