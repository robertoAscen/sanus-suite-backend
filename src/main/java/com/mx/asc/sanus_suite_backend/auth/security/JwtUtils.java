package com.mx.asc.sanus_suite_backend.auth.security;

import com.mx.asc.log.bean.LogBean;
import com.mx.asc.log.service.LoggerAscService;
import com.mx.asc.sanus_suite_backend.auth.entities.UserEntity;
import com.mx.asc.sanus_suite_backend.util.config.PropertiesConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtils {

  private final PropertiesConfig propsConfig;
  private final LoggerAscService log;
  // 8 Horas de expiración expresadas en milisegundos
  private final long jwtExpirationMs = 8 * 60 * 60 * 1000;

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(propsConfig.getJwtSecret().getBytes());
  }

  // Genera el Token cuando el usuario hace login de forma exitosa
  public String generateJwtToken(UserEntity user) {

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando metodo generateJwtToken] "))
      .build());

    Map<String, Object> claims = new HashMap<>();
    claims.put("tenantId", user.getTenantId());
    claims.put("fullName", user.getFullName());
    claims.put("roles", user.getRoles().stream()
      .map(Enum::name)
      .collect(Collectors.toList()));

    return Jwts.builder()
      .setClaims(claims)
      .setSubject(user.getUsername())
      .setIssuedAt(new Date())
      .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
      .signWith(getSigningKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  // Extrae el correo/usuario del token
  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(getSigningKey())
      .build()
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      logJwtWarning("Firma JWT inválida", e.getMessage());
    } catch (ExpiredJwtException e) {
      logJwtWarning("El token JWT ha expirado", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logJwtWarning("Token JWT no soportado", e.getMessage());
    } catch (IllegalArgumentException e) {
      logJwtWarning("La cadena de claims JWT está vacía", e.getMessage());
    }
    return false;
  }

  private void logJwtWarning(String mensajeContexto, String excepcionMensaje) {
    String traceId = org.apache.logging.log4j.ThreadContext.get("id");
    log.warn(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Seguridad JWT] %s. Folio: %s", mensajeContexto, traceId))
      .data(excepcionMensaje)
      .build());
  }
}
