package com.mx.asc.sanus_suite_backend.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import com.mx.asc.sanus_suite_backend.util.responses.ResponseError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

    String traceId = ThreadContext.get("id");

    // Construimos el DTO idéntico al de tu ResponseEntityHandling
    ResponseError errorBody = ResponseError.builder().codigo(CodigosResponse.CODIGO_401.getCodigo()) // Asegúrate de tener el código 401 mapeado
      .mensaje("Acceso denegado. Credenciales de autenticación inválidas o expiradas.").folio(traceId).info("https://sanus-developer.sanusmed.com.mx/errors#401").detalles(List.of(authException.getMessage() != null ? authException.getMessage() : "Token ausente o inválido")).build();

    // Escribimos la respuesta directamente en el stream HTTP de forma manual
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    objectMapper.writeValue(response.getOutputStream(), errorBody);
  }
}