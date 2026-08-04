package com.mx.asc.sanus_suite_backend.expedientes.services.impl;

import com.mx.asc.log.bean.LogBean;
import com.mx.asc.log.service.LoggerAscService;
import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.expedientes.repositories.ExpedienteRepository;
import com.mx.asc.sanus_suite_backend.expedientes.services.ExpedienteService;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.util.exceptions.ExceptionGenerica;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExpedienteServiceImpl implements ExpedienteService {

  private final ExpedienteRepository expedienteRepository;
  private final LoggerAscService log;

  @Override
  @Transactional
  public Expediente crearExpedienteBase(Paciente paciente, String tenantId) {

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando metodo generarExpedienteBase] Paciente: %s | Tenant: %s", paciente, tenantId))
      .build());

    int yearActual = LocalDate.now().getYear();
    LocalDateTime inicioYear = LocalDateTime.of(yearActual, 1, 1, 0, 0, 0);
    LocalDateTime finYear= LocalDateTime.of(yearActual, 12, 31, 23, 59, 59);
    Long consecutivo = expedienteRepository.countByTenantIdAndFechas(tenantId, inicioYear, finYear) + 1;
    String folioExpediente = String.format("EXP-"+tenantId+"-%d-%04d", yearActual, consecutivo);

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Numero de expediente asignado] Paciente: %s | Tenant: %s | Expediente: %s", paciente, tenantId, folioExpediente))
      .build());

    Expediente exp = new Expediente();
    exp.setPaciente(paciente);
    exp.setNumeroExpediente(folioExpediente);
    exp.setTenantId(tenantId);
    expedienteRepository.save(exp);

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[El expediente se guardo en la base de datos exitosamente] Expediente: %s", exp))
      .build());

    return exp;
  }

  @Override
  public Optional<Expediente> findByPacienteIdAndTenantId(Long pacienteId, String tenantId) {
    String traceId = ThreadContext.get("id");
    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Buscando expediente por PacienteId: %d y Tenant: %s]", pacienteId, tenantId))
      .build());
    return expedienteRepository.findByPacienteIdAndTenantId(pacienteId, tenantId);
  }

  @Override
  public Expediente findByNumeroExpedienteAndTenantId(String numeroExpediente, String tenantId) {
    String traceId = ThreadContext.get("id");
    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Buscando expediente por numero de expediente: %s y Tenant: %s]", numeroExpediente, tenantId))
      .build());
    return expedienteRepository.findByNumeroExpedienteAndTenantId(numeroExpediente, tenantId)
      .orElseThrow(() -> ExceptionGenerica.lanzar404(traceId, "No se encontro expediente con esos datos"));
  }
}
