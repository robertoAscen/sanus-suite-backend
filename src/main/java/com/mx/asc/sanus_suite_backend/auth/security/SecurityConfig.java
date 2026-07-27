package com.mx.asc.sanus_suite_backend.auth.security;

import com.mx.asc.sanus_suite_backend.auth.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permite usar @PreAuthorize("hasRole('ROLE_ADMIN')") en tus controladores
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenFilter jwtTokenFilter;
  private final JwtAuthenticationEntryPoint unauthorizedHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      // 1. Rescatado de la clase vieja: Activa la configuración de CORS por defecto
      .cors(Customizer.withDefaults())

      // 2. Deshabilitamos CSRF (API REST sin estado)
      .csrf(AbstractHttpConfigurer::disable)

      // 3. Política de sesión STATELESS (Sin cookies/sesiones en servidor)
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

      // 4. Reglas de control de acceso para Sanus Suite
      .authorizeHttpRequests(auth -> auth
        // El endpoint de login es de libre acceso para que puedan firmarse
        .requestMatchers("/api/v1/auth/login", "/api/v1/auth/register").permitAll()

        // Permite las peticiones de diagnóstico previas de CORS (Preflight requests)
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

        // Cualquier otra ruta médica o de usuarios requerirá obligatoriamente el Token JWT válido
        .anyRequest().authenticated()
      )
      .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

    // 5. Acoplamos tu filtro interceptor de JWT antes del validador por defecto de Spring
    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  // ==========================================
  // INYECTA ESTE BEAN ABAJO PARA SOLUCIONAR EL ERROR
  // ==========================================
  @Bean
  public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
    org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();

    // Al usar 'allowedOriginPatterns' con un patrón flexible, Spring cumple la norma HTTP
    configuration.addAllowedOriginPattern("*");

    configuration.addAllowedMethod("*"); // Permite GET, POST, PUT, DELETE, OPTIONS
    configuration.addAllowedHeader("*"); // Permite todas las cabeceras (incluyendo tu Token JWT)
    configuration.setAllowCredentials(true); // Mantiene activa la compatibilidad de credenciales

    org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  // Bean crucial para procesar hashes seguros de contraseñas de tus usuarios
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Manejador central que coordina el proceso de Login
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }
}