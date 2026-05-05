package com.mx.asc.sanus_suite_backend;

import com.mx.asc.sanus_suite_backend.util.config.AuditorAwareImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorProvider") // Activa la auditoría
@SpringBootApplication(scanBasePackages = {
  "com.mx.asc.sanus_suite_backend", // Tu proyecto actual
  "com.mx.asc.log"                  // El paquete de tu librería betterlog
})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

  @Bean
  public AuditorAware<String> auditorProvider() {
    return new AuditorAwareImpl();
  }
}
