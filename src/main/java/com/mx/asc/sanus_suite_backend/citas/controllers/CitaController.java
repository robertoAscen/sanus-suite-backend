package com.mx.asc.sanus_suite_backend.citas.controller;

import com.mx.asc.sanus_suite_backend.citas.dtos.CambiarEstadoCitaDto;
import com.mx.asc.sanus_suite_backend.citas.dtos.CitaRequestDto;
import com.mx.asc.sanus_suite_backend.citas.dtos.CitaResponseDto;
import com.mx.asc.sanus_suite_backend.handlers.CitaHandler;
import com.mx.asc.sanus_suite_backend.util.constants.Constantes;
import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import com.mx.asc.sanus_suite_backend.util.responses.RespuestaApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/citas/api/v1")
@RequiredArgsConstructor
public class CitaController {

  private final CitaHandler citaHandler;

  @PostMapping("/agendar")
  public ResponseEntity<RespuestaApi<Long>> agendarCita(
    @Valid @RequestBody CitaRequestDto dto,
    @RequestHeader("x-tenant-id") String tenantId,
    @RequestHeader("x-usuario-id") Long usuarioId) {

    String traceId = ThreadContext.get("id");
    Long citaId = citaHandler.agendarCita(dto, tenantId, usuarioId);

    return RespuestaApi.buildResponse(
      traceId,
      "Cita agendada exitosamente",
      citaId,
      CodigosResponse.CODIGO_201
    );
  }

  @GetMapping("/agenda")
  public ResponseEntity<RespuestaApi<List<CitaResponseDto>>> obtenerAgenda(
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
    @RequestHeader("x-tenant-id") String tenantId,
    @RequestHeader("x-usuario-id") Long usuarioId) {

    String traceId = ThreadContext.get("id");
    List<CitaResponseDto> citas = citaHandler.obtenerAgendaMedico(inicio, fin, tenantId, usuarioId);

    return RespuestaApi.buildResponse(
      traceId,
      Constantes.SUCCESS_OPERATION,
      citas,
      CodigosResponse.CODIGO_200
    );
  }

  @PutMapping("/cambiar-estado")
  public ResponseEntity<RespuestaApi<Void>> cambiarEstado(
    @Valid @RequestBody CambiarEstadoCitaDto dto,
    @RequestHeader("x-tenant-id") String tenantId) {

    String traceId = ThreadContext.get("id");
    citaHandler.cambiarEstado(dto, tenantId);

    return RespuestaApi.buildResponse(
      traceId,
      "Estatus de cita actualizado correctamente",
      null,
      CodigosResponse.CODIGO_200
    );
  }
}