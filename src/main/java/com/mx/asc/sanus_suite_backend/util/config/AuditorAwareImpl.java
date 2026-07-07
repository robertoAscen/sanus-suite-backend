package com.mx.asc.sanus_suite_backend.util.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    // Le pedimos a Spring el contexto de autenticación que fijó el filtro
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.of("SISTEMA_SANUS");
    }

    // Retornamos el nombre ("Doc-1") de forma directa y blindada
    return Optional.of(authentication.getName());
  }
}