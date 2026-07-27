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
    // !!! ESTA ES LA LÍNEA MÁGICA QUE FALTA !!!
    // Le dice a Log4j que propague el ThreadContext a sub-hilos de BD/Hibernate
    System.setProperty("log4j2.isThreadContextMapInheritable", "true");
    SpringApplication.run(Application.class, args);
	}
}
