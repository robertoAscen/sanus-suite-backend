package com.mx.asc.sanus_suite_backend.historia_clinica.controller;

import com.mx.asc.sanus_suite_backend.handlers.HistoriaClinicaHandler;
import com.mx.asc.sanus_suite_backend.historia_clinica.entities.HistoriaClinica;
import com.mx.asc.sanus_suite_backend.historia_clinica.services.HistoriaClinicaService;
import com.mx.asc.sanus_suite_backend.util.responses.RespuestaApi;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/historias-clinicas/api/v1")
public class HistoriaClinicaController {

  private final HistoriaClinicaService service;
  private final HistoriaClinicaHandler historiaClinicaHandler;

  @GetMapping("/expediente/{numeroExpediente}")
  public ResponseEntity<RespuestaApi> obtenerPorExpediente(
    @PathVariable String numeroExpediente, @RequestHeader("x-tenant-id") String tenantId) {
    String traceId = ThreadContext.get("id");
    HistoriaClinica hc = historiaClinicaHandler.obtenerHistoriaClinicaPorExpedienteYTenantId(numeroExpediente, tenantId);
    return buildResponse(traceId, "Operación exitosa", hc, HttpStatus.OK);
  }

  @PostMapping("/guardar")
  public ResponseEntity<RespuestaApi> guardarOActualizar(
    @RequestBody HistoriaClinica historia, @RequestHeader("x-tenant-id") String tenantId) {
    String traceId = ThreadContext.get("id");
    HistoriaClinica guardada = service.guardarOActualizar(historia, tenantId);
    return buildResponse(traceId, "Historia clínica guardada correctamente", guardada, HttpStatus.CREATED);
  }

  @PutMapping("/firmar/{id}/medico/{medicoId}")
  public ResponseEntity<RespuestaApi> firmarDocumento(
    @PathVariable Long id, @PathVariable Long medicoId, @RequestHeader("x-tenant-id") String tenantId) {
    String traceId = ThreadContext.get("id");
    HistoriaClinica firmada = service.firmarHistoriaClinica(id, medicoId, tenantId);
    return buildResponse(traceId, "Documento clínico firmado y bloqueado con éxito", firmada, HttpStatus.OK);
  }

  private ResponseEntity<RespuestaApi> buildResponse(String folio, String mensaje, Object resultado, HttpStatus status) {
    return new ResponseEntity<>(
      RespuestaApi.builder().folio(folio).mensaje(mensaje).resultado(resultado).build(),
      status
    );
  }
}