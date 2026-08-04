package com.mx.asc.sanus_suite_backend.util.exceptions;

import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ExceptionGenerica extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private final String codigo;
  private final String mensaje;
  private final String folio;
  private final String info;
  private final List<String> detalles;
  private final CodigosResponse codigosRespuesta;

  @Builder
  public ExceptionGenerica(
    String folio,
    String info,
    List<String> detalles,
    CodigosResponse codigosRespuesta
  ) {
    super();
    this.folio = folio;
    this.info = info;
    this.detalles = new ArrayList<>(detalles);
    this.codigosRespuesta = codigosRespuesta;
    this.codigo = codigosRespuesta.getCodigo();
    this.mensaje = codigosRespuesta.getDescripcion();
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }

  public static ExceptionGenerica lanzar404(String traceId, String mensaje) {
    return ExceptionGenerica.builder()
      .folio(traceId)
      .info(mensaje)
      .detalles(List.of(mensaje))
      .codigosRespuesta(CodigosResponse.CODIGO_404)
      .build();
  }

  public static ExceptionGenerica lanzar400(String traceId, String mensaje) {
    return ExceptionGenerica.builder()
      .folio(traceId)
      .info(mensaje)
      .detalles(List.of(mensaje))
      .codigosRespuesta(CodigosResponse.CODIGO_400)
      .build();
  }
}
