package com.mx.asc.sanus_suite_backend.pacientes.controller;

import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.pacientes.services.PacienteService;
import com.mx.asc.sanus_suite_backend.util.constants.Constantes;
import com.mx.asc.sanus_suite_backend.util.responses.RespuestaApi;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constantes.PACIENTES + Constantes.API + Constantes.V1)
public class PacienteController {

  private PacienteService pacienteService;

  public PacienteController(PacienteService pacienteService){
    this.pacienteService = pacienteService;
  }

  @PostMapping(Constantes.ALTA_PACIENTE)
  public ResponseEntity<RespuestaApi> altaPaciente(
    @RequestBody @Valid Paciente paciente, @RequestHeader String tenantId) {
    return pacienteService.altaPaciente(paciente, tenantId);
  }

  @GetMapping(Constantes.LISTAR_PACIENTES)
  public ResponseEntity<RespuestaApi> listarPacientes(@RequestHeader String tenantId) {
    return pacienteService.listaPacientes(tenantId);
  }

  @DeleteMapping(Constantes.BAJA_PACIENTE+Constantes.ID)
  public  ResponseEntity<RespuestaApi> darDeBaja(@PathVariable Long id, @RequestHeader String tenantId){
    return pacienteService.bajaPaciente(id, tenantId);
  }
}
