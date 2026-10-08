package com.mx.asc.sanus_suite_backend.receta_medica.controller;

import com.mx.asc.sanus_suite_backend.handlers.RecetaMedicaHandler;
import com.mx.asc.sanus_suite_backend.receta_medica.dtos.RecetaMedicaRequestDto;
import com.mx.asc.sanus_suite_backend.receta_medica.dtos.RecetaMedicaResponseDto;
import com.mx.asc.sanus_suite_backend.util.constants.Constantes;
import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import com.mx.asc.sanus_suite_backend.util.responses.RespuestaApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/recetas/api/v1")
@RequiredArgsConstructor
public class RecetaMedicaController {

  private final RecetaMedicaHandler recetaMedicaHandler;

  @PostMapping("/guardar")
  public ResponseEntity<RespuestaApi<Long>> crearReceta(
    @Valid @RequestBody RecetaMedicaRequestDto dto,
    @RequestHeader("x-tenant-id") String tenantId,
    @RequestHeader("x-usuario-id") Long usuarioId) {

    String traceId = ThreadContext.get("id");

    Long recetaId = recetaMedicaHandler.crearReceta(dto, tenantId, usuarioId);

    return RespuestaApi.buildResponse(
      traceId,
      Constantes.SUCCESS_OPERATION,
      recetaId,
      CodigosResponse.CODIGO_200
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<RespuestaApi<RecetaMedicaResponseDto>> obtenerPorId(
    @PathVariable Long id,
    @RequestHeader("X-Tenant-Id") String tenantId,
    @RequestHeader("X-Usuario-Id") Long usuarioId) {

    String traceId = ThreadContext.get("id");

    RecetaMedicaResponseDto response = recetaMedicaHandler.obtenerPorId(id, tenantId, usuarioId);

    return RespuestaApi.buildResponse(
      traceId,
      Constantes.SUCCESS_OPERATION,
      response,
      CodigosResponse.CODIGO_200
    );
  }

  @GetMapping("/{id}/pdf")
  public ResponseEntity<InputStreamResource> descargarPdf(
    @PathVariable Long id,
    @RequestHeader("X-Tenant-Id") String tenantId,
    @RequestHeader("X-Usuario-Id") Long usuarioId) {

    String traceId = ThreadContext.get("id");
    ByteArrayInputStream pdfStream = recetaMedicaHandler.generarPdfReceta(id, tenantId, usuarioId);
    InputStreamResource resource = new InputStreamResource(pdfStream);

    String nombreArchivo = String.format("receta_medica_%d.pdf", id);

    return RespuestaApi.buildFileResponse(
      resource,
      nombreArchivo,
      MediaType.APPLICATION_PDF,
      CodigosResponse.CODIGO_200
    );
  }
}
