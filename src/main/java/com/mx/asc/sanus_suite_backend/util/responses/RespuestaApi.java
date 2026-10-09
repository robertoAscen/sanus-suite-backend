package com.mx.asc.sanus_suite_backend.util.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

  public static <T> ResponseEntity<T> buildFileResponse(
    T resource,
    String nombreArchivo,
    MediaType mediaType,
    CodigosResponse codigosResponse) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(mediaType);
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + nombreArchivo + "\"");

    return new ResponseEntity<>(resource, headers, codigosResponse.getHttpStatus());
  }
}