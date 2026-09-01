package com.mx.asc.sanus_suite_backend.nota_evolucion.controller;

import com.mx.asc.sanus_suite_backend.handlers.NotaEvolucionHandler;
import com.mx.asc.sanus_suite_backend.nota_evolucion.dtos.NotaEvolucionRequestDto;
import com.mx.asc.sanus_suite_backend.nota_evolucion.dtos.NotaEvolucionResponseDto;
import com.mx.asc.sanus_suite_backend.nota_evolucion.dtos.NotaEvolucionUpdateDto;
import com.mx.asc.sanus_suite_backend.nota_evolucion.entities.NotaEvolucion;
import com.mx.asc.sanus_suite_backend.nota_evolucion.services.NotaEvolucionService;
import com.mx.asc.sanus_suite_backend.util.constants.Constantes;
import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import com.mx.asc.sanus_suite_backend.util.responses.RespuestaApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notas-evolucion/api/v1")
@RequiredArgsConstructor
public class NotaEvolucionController {

  private final NotaEvolucionHandler notaEvolucionHandler;

  @PostMapping("/guardar")
  public ResponseEntity<RespuestaApi<NotaEvolucionResponseDto>> guardarOActualizar(
    @Valid @RequestBody NotaEvolucionRequestDto dto,
    @RequestHeader("x-tenant-id") String tenantId,
    @RequestHeader("x-usuario-id") Long usuarioId) {

    String traceId = ThreadContext.get("id");

    notaEvolucionHandler.guardarOActualizar(dto, tenantId, usuarioId);

    return RespuestaApi.buildResponse(
      traceId,
      "Nota Evolucion guardada exitosamente",
      null,
      CodigosResponse.CODIGO_201
    );
  }

  @GetMapping("/expediente/{numExpediente}")
  public ResponseEntity<RespuestaApi<List<NotaEvolucionResponseDto>>> obtenerPorExpediente(
    @PathVariable String numExpediente,
    @RequestHeader("x-tenant-id") String tenantId,
    @RequestHeader("x-usuario-id") Long usuarioId) {

    String traceId = ThreadContext.get("id");

    List<NotaEvolucionResponseDto> resultado = notaEvolucionHandler.notasEvolucionList(numExpediente, tenantId, usuarioId);

    return RespuestaApi.buildResponse(
      traceId,
      "Notas evolucion para el expediente: " + numExpediente,
      resultado,
      CodigosResponse.CODIGO_200
    );
  }

  @PutMapping("/actualizar")
  public ResponseEntity<RespuestaApi<Long>> actualizar(
    @Valid @RequestBody NotaEvolucionUpdateDto dto,
    @RequestHeader("x-tenant-id") String tenantId,
    @RequestHeader("x-usuario-id") Long usuarioId) {

    String traceId = ThreadContext.get("id");

    Long notaId = notaEvolucionHandler.actualizar(dto, tenantId, usuarioId);

    String mensaje = Boolean.TRUE.equals(dto.getFirmado())
      ? "Nota de evolución actualizada y firmada exitosamente"
      : "Borrador de nota de evolución actualizado correctamente";

    return RespuestaApi.buildResponse(
      traceId,
      mensaje,
      notaId,
      CodigosResponse.CODIGO_200
    );

  }

  @GetMapping("/{id}")
  public ResponseEntity<RespuestaApi<NotaEvolucionResponseDto>> obtenerPorId(
    @PathVariable Long id,
    @RequestHeader("x-tenant-id") String tenantId,
    @RequestHeader("x-usuario-id") Long usuarioId) {

    String traceId = ThreadContext.get("id");

    NotaEvolucionResponseDto notaEvolucionResponseDto = notaEvolucionHandler.obtenerNotaPorIdYTenantId(id, tenantId, usuarioId);

    return RespuestaApi.buildResponse(
      traceId,
      Constantes.SUCCESS_OPERATION,
      notaEvolucionResponseDto,
      CodigosResponse.CODIGO_200
    );
  }
}
