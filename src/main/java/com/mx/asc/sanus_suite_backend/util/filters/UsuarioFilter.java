package com.mx.asc.sanus_suite_backend.util.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class UsuarioFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String usuarioHeader = httpRequest.getHeader("x-usuario");

    String usuarioFinal = (usuarioHeader != null && !usuarioHeader.isBlank()) ? usuarioHeader : "SISTEMA_SANUS";

    // 1. Lo dejamos en Log4j para tus logs normales de consola/archivo
    ThreadContext.put("usuario", usuarioFinal);

    // 2. ¡LA CLAVE!: Lo inyectamos en el contenedor nativo de Spring
    // Creamos un token de autenticación plano con el nombre del usuario
    UsernamePasswordAuthenticationToken authentication =
      new UsernamePasswordAuthenticationToken(usuarioFinal, null, null);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    try {
      chain.doFilter(request, response);
    } finally {
      // Al terminar la petición, limpiamos ambos contextos de forma segura
      ThreadContext.remove("usuario");
      SecurityContextHolder.clearContext();
    }
  }
}