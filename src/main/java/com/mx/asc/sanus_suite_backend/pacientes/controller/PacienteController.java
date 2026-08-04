package com.mx.asc.sanus_suite_backend.pacientes.controller;

import com.mx.asc.sanus_suite_backend.handlers.PacienteHandler;
import com.mx.asc.sanus_suite_backend.pacientes.dtos.PacienteDto;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;import com.mx.asc.sanus_suite_backend.util.constants.Constantes;
import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import com.mx.asc.sanus_suite_backend.util.responses.RespuestaApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PACIENTES + Constantes.API + Constantes.V1)
@RequiredArgsConstructor
public class PacienteController {

  private final PacienteHandler pacienteHandler;

  @PostMapping(Constantes.GUARDAR)
  public ResponseEntity<RespuestaApi<PacienteDto>> guardar(
    @RequestBody @Valid Paciente paciente, @RequestHeader(Constantes.HEADER_X_TENANT_ID) String tenantId) {
    String traceId = ThreadContext.get("id");
    return RespuestaApi.buildResponse(traceId, Constantes.SUCCESS_OPERATION, pacienteHandler.altaPaciente(paciente, tenantId), CodigosResponse.CODIGO_201);
  }

  @GetMapping(Constantes.LISTAR)
  public ResponseEntity<RespuestaApi<List<PacienteDto>>> listar(@RequestHeader(Constantes.HEADER_X_TENANT_ID) String tenantId) {
    String traceId = ThreadContext.get("id");
    return RespuestaApi.buildResponse(traceId, Constantes.SUCCESS_OPERATION, pacienteHandler.listaPacientes(tenantId), CodigosResponse.CODIGO_200);
  }

  @DeleteMapping(Constantes.BAJA + Constantes.ID)
  public  ResponseEntity<RespuestaApi<Void>> Baja(@PathVariable Long id, @RequestHeader(Constantes.HEADER_X_TENANT_ID) String tenantId){
    String traceId = ThreadContext.get("id");
    pacienteHandler.bajaLogicaPaciente(id, tenantId);
    return RespuestaApi.buildResponse(traceId, Constantes.SUCCESS_OPERATION, null, CodigosResponse.CODIGO_200);
  }
}
