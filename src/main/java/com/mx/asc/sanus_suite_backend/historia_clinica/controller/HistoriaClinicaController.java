package com.mx.asc.sanus_suite_backend.historia_clinica.controller;

import com.mx.asc.sanus_suite_backend.handlers.HistoriaClinicaHandler;
import com.mx.asc.sanus_suite_backend.historia_clinica.dtos.HistoriaClinicaRequestDto;
import com.mx.asc.sanus_suite_backend.historia_clinica.dtos.HistoriaClinicaResponseDto;
import com.mx.asc.sanus_suite_backend.util.constants.Constantes;
import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import com.mx.asc.sanus_suite_backend.util.responses.RespuestaApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constantes.HISTORIAS_CLINICAS + Constantes.API + Constantes.V1)
@RequiredArgsConstructor
public class HistoriaClinicaController {

  private final HistoriaClinicaHandler historiaClinicaHandler;

  @PostMapping(Constantes.GUARDAR)
  public ResponseEntity<RespuestaApi<HistoriaClinicaResponseDto>> guardar(
    @Valid @RequestBody HistoriaClinicaRequestDto requestDto,
    @RequestHeader(Constantes.HEADER_X_TENANT_ID) String tenantId) {

    String traceId = ThreadContext.get("id");
    HistoriaClinicaResponseDto resultado = historiaClinicaHandler.guardarOActualizar(requestDto, tenantId);
    return RespuestaApi.buildResponse(
      traceId,
      "Historia clínica guardada exitosamente",
      resultado,
      CodigosResponse.CODIGO_200
    );
  }

  @GetMapping(Constantes.EXPEDIENTE + Constantes.NUMERO_EXPEDIENTE)
  public ResponseEntity<RespuestaApi<HistoriaClinicaResponseDto>> obtenerPorExpediente(
    @PathVariable String numeroExpediente,
    @RequestHeader(Constantes.HEADER_X_TENANT_ID) String tenantId) {

    String traceId = ThreadContext.get("id");
    HistoriaClinicaResponseDto resultado = historiaClinicaHandler.obtenerPorNumeroExpediente(numeroExpediente, tenantId);
    return RespuestaApi.buildResponse(
      traceId,
      "Historia clínica recuperada con éxito",
      resultado,
      CodigosResponse.CODIGO_200
    );
  }

  @PostMapping(Constantes.FIRMAR + Constantes.ID)
  public ResponseEntity<RespuestaApi<HistoriaClinicaResponseDto>> firmar(
    @PathVariable Long id,
    @RequestHeader(Constantes.HEADER_X_USUARIO_ID) Long usuarioId,
    @RequestHeader(Constantes.HEADER_X_TENANT_ID) String tenantId) {

    String traceId = ThreadContext.get("id");
    HistoriaClinicaResponseDto resultado = historiaClinicaHandler.firmarHistoriaClinica(id,usuarioId, tenantId);
    return RespuestaApi.buildResponse(
      traceId,
      "Historia clinica firmada con éxito",
      resultado,
      CodigosResponse.CODIGO_200
    );
  }
}