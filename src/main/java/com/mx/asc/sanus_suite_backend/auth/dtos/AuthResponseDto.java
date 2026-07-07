package com.mx.asc.sanus_suite_backend.auth.dtos;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class AuthResponseDto {
  private String token;
  private String username;
  private String fullName;
  private String tenantId;
  private Set<String> roles;
}
