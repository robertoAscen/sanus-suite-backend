package com.mx.asc.sanus_suite_backend.pacientes.services.impl;

import com.mx.asc.log.bean.LogBean;
import com.mx.asc.log.service.LoggerAscService;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.pacientes.repositories.PacienteRepository;
import com.mx.asc.sanus_suite_backend.pacientes.services.PacienteService;
import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import com.mx.asc.sanus_suite_backend.util.exceptions.ExceptionGenerica;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PacienteServiceImpl implements PacienteService {

  private final PacienteRepository pacienteRepository;
  private final LoggerAscService log;

  @Override
  @Transactional
  public Paciente altaPaciente(Paciente paciente, String tenantId) {

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando metodo altaPaciente] Paciente: %s | Tenant: %s ", paciente, tenantId))
      .build());

    log.info(
      LogBean.builder()
        .clase(getClass())
        .message(String.format("[Validando existencia del paciente %s en la base de datos] - ", paciente))
        .build()
    );

    if (pacienteRepository.existsByCurpAndTenantId(paciente.getCurp(), tenantId)) {
      throw ExceptionGenerica.lanzar400(traceId, "El paciente ya cuenta con un registro en esta clínica");
    }
    log.info(
      LogBean.builder()
        .clase(getClass())
        .message(String.format("[Se valido la existencia del paciente %s en la base de datos, no se encontro registro, se hara el registro pertinente]", paciente))
        .build()
    );

    log.info(
      LogBean.builder()
        .clase(getClass())
        .message("[Registrado paciente en la base de datos...]")
        .build()
    );

    paciente.setTenantId(tenantId);
    paciente.setActivo(true);
    Paciente newPaciente = pacienteRepository.save(paciente);

    log.info(
      LogBean.builder()
        .clase(getClass())
        .message(String.format("[Paciente registrado exitosamente] - %s", newPaciente))
        .build()
    );

    log.info(
      LogBean.builder()
        .clase(getClass())
        .data(String.format("[Respuesta del metodo altaPaciente] - %s", newPaciente))
        .build()
    );

    return newPaciente;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Paciente> listaPacientes(String tenantId) {

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando metodo listaPacientes] Tenant: %s ", tenantId))
      .build());

    List<Paciente> pacientes = (List<Paciente>) pacienteRepository.findAllByTenantId(tenantId);

    log.info(
      LogBean.builder()
        .clase(getClass())
        .message(String.format("[Pacientes encontrados en la base de datos] - %s", pacientes))
        .build()
    );

    return pacientes;
  }

  @Override
  @Transactional
  public void bajaPaciente(Long id, String tenantId) {

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando metodo bajaPaciente] Id: %d | Tenant: %s", id, tenantId))
      .build());

    Paciente paciente = pacienteRepository.findByIdAndTenantId(id, tenantId)
      .orElseThrow(() -> ExceptionGenerica.lanzar404(traceId, "El paciente no existe o no pertenece a esta clínica"));

    log.info(LogBean.builder()
      .clase(getClass())
      .message("[Dando de baja el paciente....]")
      .build());

    paciente.setActivo(false);
    pacienteRepository.save(paciente);

    log.info(LogBean.builder()
      .clase(getClass())
      .message("[Baja del paciente exitosa]")
      .build());
  }

  @Override
  public Paciente obtenerPacientePorIdAndTenantId(Long id, String tenantId) {

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando metodo obtener paciente por id] Id: %d | Tenant: %s", id, tenantId))
      .build());

    Paciente paciente = pacienteRepository.findByIdAndTenantId(id, tenantId).orElseThrow(
      () -> ExceptionGenerica.lanzar404(traceId, "El paciente no existe o no pertenece a esta clinica")
      );

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Paciente encontrado ] Paciente: %s", paciente))
      .build());

    return paciente;
  }
}
