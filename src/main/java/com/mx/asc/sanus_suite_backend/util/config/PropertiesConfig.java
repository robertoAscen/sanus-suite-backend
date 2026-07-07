package com.mx.asc.sanus_suite_backend.util.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@Getter
public class PropertiesConfig {

  @Value("${sanus.jwt.secret}")
  private String jwtSecret;

}
