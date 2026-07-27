package com.mx.asc.sanus_suite_backend.util.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespuestaApi<T> {
  private String mensaje;
  private String folio;
  private Object resultado;

  public static <T> ResponseEntity<RespuestaApi<T>> buildResponse(String folio, String mensaje, T resultado, CodigosResponse codigosResponse) {
    RespuestaApi<T> body = RespuestaApi.<T>builder()
      .folio(folio)
      .mensaje(mensaje)
      .resultado(resultado)
      .build();
    return new ResponseEntity<>(body, codigosResponse.getHttpStatus());
  }
}
