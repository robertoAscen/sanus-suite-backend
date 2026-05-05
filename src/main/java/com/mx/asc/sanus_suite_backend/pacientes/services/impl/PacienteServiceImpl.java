package com.mx.asc.sanus_suite_backend.pacientes.services.impl;

import com.mx.asc.log.bean.LogBean;
import com.mx.asc.log.service.LoggerAscService;
import com.mx.asc.sanus_suite_backend.pacientes.dtos.PacienteDto;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.pacientes.repositories.PacienteRepository;
import com.mx.asc.sanus_suite_backend.pacientes.services.PacienteService;
import com.mx.asc.sanus_suite_backend.util.constants.Constantes;
import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import com.mx.asc.sanus_suite_backend.util.exceptions.ExceptionGenerica;
import com.mx.asc.sanus_suite_backend.util.responses.RespuestaApi;
import org.apache.logging.log4j.ThreadContext;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements PacienteService {

  private PacienteRepository pacienteRepository;
  private ModelMapper modelMapper;
  private LoggerAscService log;

  public PacienteServiceImpl(PacienteRepository pacienteRepository, ModelMapper modelMapper, LoggerAscService log) {
    this.pacienteRepository = pacienteRepository;
    this.modelMapper = modelMapper;
    this.log = log;
  }

  @Override
  @Transactional
  public ResponseEntity<RespuestaApi> altaPaciente(Paciente paciente, String tenantId) {
    String traceId = ThreadContext.get("id");
    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando metodo altaPaciente] Paciente: %s | Tenant: %s ", paciente, tenantId))
      .build());
      ResponseEntity<RespuestaApi> response;
      log.info(
        LogBean.builder()
          .clase(getClass())
          .message(String.format("[Validando existencia del paciente %s en la base de datos] - ", paciente))
          .build()
      );
      Paciente findPacienteDb = pacienteRepository.findByCurpAndTenantId(paciente.getCurp(), tenantId);
      if (findPacienteDb == null) {
        paciente.setTenantId(tenantId);
        log.info(
          LogBean.builder()
            .clase(getClass())
            .message(String.format("[Se valido la existencia del paciente %s en la base de datos, no se encontro registro, se hara el registro pertinente]", paciente))
            .build()
        );
        log.info(
          LogBean.builder()
            .clase(getClass())
            .message("[Registrado paciente en la base de datos]")
            .build()
        );
        Paciente newPaciente = pacienteRepository.save(paciente);
        log.info(
          LogBean.builder()
            .clase(getClass())
            .message(String.format("[Paciente registrado exitosamente] - %s", newPaciente))
            .build()
        );
        response = getResponse(traceId, Constantes.SUCCESS_OPERATION, modelMapper.map(newPaciente, PacienteDto.class), CodigosResponse.CODIGO_201.getHttpStatus());
        log.info(
          LogBean.builder()
            .clase(getClass())
            .data(String.format("[Respuesta del metodo altaPaciente] - %s", response))
            .build()
        );
        return response;
      }
      log.info(
        LogBean.builder()
          .clase(getClass())
          .message(String.format("[El paciente %s ya esta registrado en la base de datos, no es necesario que lo vuelva a registrar]", findPacienteDb))
          .build()
      );
      response = getResponse(traceId, Constantes.OPERATION_DUPLICATED, null, CodigosResponse.CODIGO_202.getHttpStatus());
      log.info(
        LogBean.builder()
          .clase(getClass())
          .data(String.format("[Respuesta del metodo altaPaciente] - %s", response))
          .build()
      );
      return response;
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntity<RespuestaApi> listaPacientes(String tenantId) {
    String traceId = ThreadContext.get("id");
      log.info(LogBean.builder()
        .clase(getClass())
        .message(String.format("[Iniciando metodo listaPacientes] Tenant: %s ", tenantId))
        .build());
      List<Paciente> pacientes = (List<Paciente>) pacienteRepository.findAllByTenantId(tenantId);
      List<PacienteDto> pacientesDto = pacientes.stream().map(paciente -> modelMapper.map(paciente, PacienteDto.class)).collect(Collectors.toList());
      log.info(
        LogBean.builder()
          .clase(getClass())
          .message(String.format("[Pacientes encontrados en la base de datos] - %s", pacientesDto))
          .build()
      );
      ResponseEntity<RespuestaApi> response = getResponse(traceId, Constantes.SUCCESS_OPERATION, pacientesDto, CodigosResponse.CODIGO_200.getHttpStatus());
      log.info(
        LogBean.builder()
          .clase(getClass())
          .message(String.format("[Respuesta del metodo listaPacientes] - %s", response))
          .build()
      );
      return response;
  }

  @Override
  @Transactional
  public ResponseEntity<RespuestaApi> bajaPaciente(Long id, String tenantId) {
    String traceId = ThreadContext.get("id");
      ResponseEntity<RespuestaApi> response;
      log.info(LogBean.builder()
        .clase(getClass())
        .message(String.format("[Iniciando metodo bajaPaciente] Id: %d | Tenant: %s", id, tenantId))
        .build());
    Paciente pacienteDb = pacienteRepository.findByIdAndTenantId(id, tenantId)
      .orElseThrow(() -> ExceptionGenerica.builder()
        .codigosRespuesta(CodigosResponse.CODIGO_404)
        .detalles(List.of("El paciente no existe o no pertenece a su clínica"))
        .build());
    log.info(LogBean.builder()
      .clase(getClass())
      .message("[Paciente encontrado en la base de datos para baja logica] - " + pacienteDb.getNombre() + " " + pacienteDb.getApellidoPaterno() + " " + pacienteDb.getApellidoMaterno())
      .build());
    pacienteRepository.deleteById(pacienteDb.getId());
    log.info(LogBean.builder()
      .clase(getClass())
      .message("[Baja del paciente exitosa]")
      .build());
    response = getResponse(traceId, Constantes.SUCCESS_OPERATION, null,
      CodigosResponse.CODIGO_200.getHttpStatus());
      log.info(LogBean.builder()
        .clase(getClass())
        .message("[Respuesta del metodo bajaPaciente]")
        .data(response)
        .build());
      return response;
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
