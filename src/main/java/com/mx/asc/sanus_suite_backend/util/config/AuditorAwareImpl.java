package com.mx.asc.sanus_suite_backend.util.config;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.data.domain.AuditorAware;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    // Por ahora lo tomamos del ThreadContext.
    // Si no existe, ponemos "SISTEMA" por defecto.
    String usuario = ThreadContext.get("usuario");
    return Optional.ofNullable(usuario != null ? usuario : "SISTEMA_SANUS");
  }
}