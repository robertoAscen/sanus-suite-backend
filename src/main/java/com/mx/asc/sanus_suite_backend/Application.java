package com.mx.asc.sanus_suite_backend;

import com.mx.asc.sanus_suite_backend.util.config.AuditorAwareImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@SpringBootApplication(scanBasePackages = {
  "com.mx.asc.sanus_suite_backend",
  "com.mx.asc.log"
})
public class Application {

	public static void main(String[] args) {
    System.setProperty("log4j2.isThreadContextMapInheritable", "true");
    SpringApplication.run(Application.class, args);
	}
}
