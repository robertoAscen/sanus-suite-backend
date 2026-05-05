package com.mx.asc.sanus_suite_backend.util.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResponseError {
  private String codigo;
  private String mensaje;
  private String folio;
  private String info;
  private List<String> detalles;
}
