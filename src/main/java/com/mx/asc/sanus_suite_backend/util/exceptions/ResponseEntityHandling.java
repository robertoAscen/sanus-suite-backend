package com.mx.asc.sanus_suite_backend.util.exceptions;

import com.mx.asc.log.bean.LogBean;
import com.mx.asc.log.service.LoggerAscService;
import com.mx.asc.sanus_suite_backend.util.constants.Constantes;
import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import com.mx.asc.sanus_suite_backend.util.responses.ResponseError;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ResponseEntityHandling {
  private final LoggerAscService log; // Nueva interfaz de la librería

  // Inyección por constructor: más limpio y fácil de testear
  public ResponseEntityHandling(LoggerAscService log) {
    this.log = log;
  }

  @ExceptionHandler(ExceptionGenerica.class)
  public ResponseEntity<ResponseError> handleExceptionGenerica(ExceptionGenerica ex) {
    // Usamos el traceId que ya viene en tu excepción
    String traceId = ThreadContext.get("id");

    // Creamos el objeto de respuesta de error
    ResponseError error = ResponseError.builder()
      .codigo(ex.getCodigosRespuesta().getCodigo())
      .mensaje(ex.getMensaje()) // Asegúrate que ExceptionGenerica tome el mensaje del Enum
      .folio(traceId)
      .info("https://sanus-developer.sanusmed.com.mx/errors#")
      .detalles(ex.getDetalles())
      .build();

    // LOG CONTROLADO: En lugar de imprimir toda la excepción, solo loggeamos el mensaje relevante.
    log.warn(LogBean.builder()
      .clase(getClass())
      .message("[Excepción de Negocio Controlada]")
      .data(error)
      .build());

    return new ResponseEntity<>(error, ex.getCodigosRespuesta().getHttpStatus());
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ResponseError> handleAllException(Throwable ex) {
    String traceId = ThreadContext.get("id");
    ResponseEntity<ResponseError> response = new ResponseEntity<>(
      ResponseError.builder()
        .codigo(CodigosResponse.CODIGO_500.getCodigo())
        .folio(traceId)
        .mensaje(CodigosResponse.CODIGO_500.getDescripcion())
        .info(Constantes.URL_ERROR)
        .detalles(Collections.singletonList(ex.getMessage()))
        .build(),
      CodigosResponse.CODIGO_500.getHttpStatus()
    );

    log.error(LogBean.builder()
      .clase(getClass())
      .message("Error interno no controlado (500)")
      .exception(ex)
      .data(response.getBody())
      .build());

    return response;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseError> handleValidationExceptions(MethodArgumentNotValidException ex) {
    String traceId = ThreadContext.get("id"); // Recuperamos el traceId del log4j2

    // Extraemos todos los errores de los campos (pueden ser varios)
    List<String> detalles = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
      .collect(Collectors.toList());

    ResponseError errorBody = ResponseError.builder()
      .codigo(CodigosResponse.CODIGO_400.getCodigo()) // Usamos el código 400 (Bad Request)
      .mensaje("Error de validación en los campos enviados")
      .folio(traceId)
      .info("https://sanus-developer.sanusmed.com.mx/errors#")
      .detalles(detalles)
      .build();

    // Logeamos como WARN porque es un error del cliente, no del servidor
    log.warn(LogBean.builder()
      .clase(getClass())
      .message("[Error de Validación de Request]")
      .data(errorBody)
      .build());

    return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ResponseError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    String traceId = ThreadContext.get("id");
    String name = ex.getName();
    String type = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "Unknown";
    Object value = ex.getValue();
    String message = String.format("'%s' debería ser un %s válido pero '%s' no lo es", name, type, value);

    ResponseEntity<ResponseError> response = new ResponseEntity<>(
      ResponseError.builder()
        .codigo(CodigosResponse.CODIGO_400.getCodigo())
        .folio(traceId)
        .mensaje(CodigosResponse.CODIGO_400.getDescripcion())
        .info(Constantes.URL_ERROR + CodigosResponse.CODIGO_400.getCodigo())
        .detalles(List.of(message))
        .build(),
      HttpStatus.BAD_REQUEST
    );

    log.warn(LogBean.builder()
      .clase(getClass())
      .message("Error de tipo de argumento")
      .data(response.getBody())
      .build());

    return response;
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<ResponseError> handleHeaders(MissingRequestHeaderException ex) {
    String traceId = ThreadContext.get("id");
    String name = ex.getHeaderName();
    List<String> detalles = List.of(String.format("El header '%s' es requerido", name));

    ResponseEntity<ResponseError> response = new ResponseEntity<>(
      ResponseError.builder()
        .codigo(CodigosResponse.CODIGO_400.getCodigo())
        .folio(traceId)
        .mensaje(CodigosResponse.CODIGO_400.getDescripcion())
        .info(Constantes.URL_ERROR + CodigosResponse.CODIGO_400.getCodigo())
        .detalles(detalles)
        .build(),
      HttpStatus.BAD_REQUEST
    );

    log.warn(LogBean.builder()
      .clase(getClass())
      .message("Header faltante en la petición: " + name)
      .data(response.getBody())
      .build());

    return response;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ResponseError> onConstraintValidationException(ConstraintViolationException e) {
    String traceId = ThreadContext.get("id");
    List<String> detalles = e.getConstraintViolations()
      .stream()
      .map(v -> v.getPropertyPath().toString() + " : " + v.getMessage())
      .collect(Collectors.toList());

    ResponseEntity<ResponseError> response = new ResponseEntity<>(
      ResponseError.builder()
        .codigo(CodigosResponse.CODIGO_400.getCodigo())
        .folio(traceId)
        .mensaje(CodigosResponse.CODIGO_400.getDescripcion())
        .info(Constantes.URL_ERROR + CodigosResponse.CODIGO_400.getCodigo())
        .detalles(detalles)
        .build(),
      HttpStatus.BAD_REQUEST
    );

    log.warn(LogBean.builder()
      .clase(getClass())
      .message("Violación de restricciones de validación")
      .data(response.getBody())
      .build());

    return response;
  }
}