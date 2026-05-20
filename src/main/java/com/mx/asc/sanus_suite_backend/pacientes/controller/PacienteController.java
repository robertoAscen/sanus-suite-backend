package com.mx.asc.sanus_suite_backend.pacientes.controller;

import com.mx.asc.sanus_suite_backend.handlers.PacienteHandler;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.pacientes.services.PacienteService;
import com.mx.asc.sanus_suite_backend.util.constants.Constantes;
import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import com.mx.asc.sanus_suite_backend.util.responses.RespuestaApi;
import jakarta.validation.Valid;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constantes.PACIENTES + Constantes.API + Constantes.V1)
public class PacienteController {

  private PacienteHandler pacienteHandler;

  public PacienteController(PacienteHandler pacienteHandler){
    this.pacienteHandler = pacienteHandler;
  }

  @PostMapping(Constantes.ALTA_PACIENTE)
  public ResponseEntity<RespuestaApi> altaPaciente(
    @RequestBody @Valid Paciente paciente, @RequestHeader String tenantId) {
    String traceId = ThreadContext.get("id");
    return getResponse(traceId, Constantes.SUCCESS_OPERATION, pacienteHandler.altaPaciente(paciente, tenantId), CodigosResponse.CODIGO_201.getHttpStatus());
  }

  @GetMapping(Constantes.LISTAR_PACIENTES)
  public ResponseEntity<RespuestaApi> listarPacientes(@RequestHeader String tenantId) {
    String traceId = ThreadContext.get("id");
    return getResponse(traceId, Constantes.SUCCESS_OPERATION, pacienteHandler.listaPacientes(tenantId), CodigosResponse.CODIGO_200.getHttpStatus());
  }

  @DeleteMapping(Constantes.BAJA_PACIENTE+Constantes.ID)
  public  ResponseEntity<RespuestaApi> darDeBaja(@PathVariable Long id, @RequestHeader String tenantId){
    String traceId = ThreadContext.get("id");
    pacienteHandler.bajaLogicaPaciente(id, tenantId);
    return getResponse(traceId, Constantes.SUCCESS_OPERATION, null, CodigosResponse.CODIGO_200.getHttpStatus());
  }

  /**
   *
   * @param folio
   * @param mensaje
   * @param resultado
   * @param status
   * @return
   */
  private ResponseEntity<RespuestaApi> getResponse(String folio, String mensaje, Object resultado, HttpStatus status) {
    return new ResponseEntity<>(RespuestaApi.builder().folio(folio).mensaje(mensaje).resultado(resultado).build(), status);
  }
}
