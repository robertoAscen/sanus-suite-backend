package com.mx.asc.sanus_suite_backend.util.config;

import jakarta.servlet.ServletRequestEvent;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir todos los endpoints
          .allowedOrigins("http://localhost:4200") // El origen de tu Angular
          .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
          .allowedHeaders("*")
          .allowCredentials(true);
      }
    };
  }

  @Bean
  public RequestContextListener requestContextListener() {
    return new RequestContextListener() {
      @Override
      public void requestDestroyed(ServletRequestEvent sre) {
        // Este evento se ejecuta al final de todo el ciclo de vida HTTP,
        // garantizando que la transacción de la BD ya se cerró por completo.
        ThreadContext.remove("usuario");
        super.requestDestroyed(sre);
      }
    };
  }
}