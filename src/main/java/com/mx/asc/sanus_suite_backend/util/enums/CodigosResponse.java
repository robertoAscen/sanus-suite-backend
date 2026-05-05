package com.mx.asc.sanus_suite_backend.util.enums;

import com.mx.asc.sanus_suite_backend.util.constants.Constantes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CodigosResponse {

  CODIGO_200(
    String.valueOf(HttpStatus.OK.value()),
    Constantes.SUCCESS_OPERATION,
    HttpStatus.OK
  ),
  CODIGO_201(
    String.valueOf(HttpStatus.CREATED.value()),
    Constantes.SUCCESS_OPERATION,
    HttpStatus.CREATED
  ),
  CODIGO_202(
    String.valueOf(HttpStatus.ACCEPTED.value()),
    Constantes.SUCCESS_OPERATION,
    HttpStatus.ACCEPTED
  ),
  CODIGO_204(
    String.valueOf(HttpStatus.NO_CONTENT.value()),
    Constantes.SUCCESS_OPERATION,
    HttpStatus.NO_CONTENT
  ),
  CODIGO_400(
    String.valueOf(HttpStatus.BAD_REQUEST.value()),
    Constantes.BAD_REQUEST,
    HttpStatus.BAD_REQUEST
  ),
  CODIGO_401(
    String.valueOf(HttpStatus.UNAUTHORIZED.value()),
    Constantes.UNAUTHORIZED,
    HttpStatus.UNAUTHORIZED
  ),
  CODIGO_404(
    String.valueOf(HttpStatus.NOT_FOUND.value()),
    Constantes.NOT_FOUND,
    HttpStatus.NOT_FOUND
  ),
  CODIGO_500(
    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
    Constantes.INTERNAL_ERROR,
    HttpStatus.INTERNAL_SERVER_ERROR
  );

  private final String codigo;
  private final String descripcion;
  private final HttpStatus httpStatus;

  /**
   * Genera el código de respuesta con el formato: [HTTP_STATUS].[SISTEMA].[CODIGO]-
   * Ejemplo: 200.MONO_SANUS_SUITE.200-
   *
   * @return String con el código formateado.
   */
  public String getCodigo() {
    return new StringBuilder()
      .append(this.httpStatus.value())
      .append(".")
      .append(Constantes.SISTEMA)
      .append(".")
      .append(this.codigo)
      .append("-")
      .toString();
  }

  /**
   * Busca un CodigoResponse por su valor de código interno.
   *
   * @param codigo El valor del código a buscar.
   * @return El enum correspondiente o CODIGO_500 por defecto.
   */
  public static CodigosResponse getByCode(String codigo) {
    for (CodigosResponse code : CodigosResponse.values()) {
      if (code.codigo.equals(codigo)) {
        return code;
      }
    }
    return CODIGO_500;
  }
}