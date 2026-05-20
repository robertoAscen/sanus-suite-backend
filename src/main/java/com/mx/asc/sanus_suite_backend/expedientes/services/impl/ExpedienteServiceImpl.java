package com.mx.asc.sanus_suite_backend.expedientes.services.impl;

import com.mx.asc.log.bean.LogBean;
import com.mx.asc.log.service.LoggerAscService;
import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.expedientes.repositories.ExpedienteRepository;
import com.mx.asc.sanus_suite_backend.expedientes.services.ExpedienteService;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.ThreadContext;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ExpedienteServiceImpl implements ExpedienteService {

  private ExpedienteRepository expedienteRepository;
  private ModelMapper modelMapper;
  private LoggerAscService log;

  public ExpedienteServiceImpl(ExpedienteRepository expedienteRepository, ModelMapper modelMapper, LoggerAscService log) {
    this.expedienteRepository = expedienteRepository;
    this.modelMapper = modelMapper;
    this.log = log;
  }

  @Override
  @Transactional
  public Expediente crearExpedienteBase(Paciente paciente, String tenantId) {
    String traceId = ThreadContext.get("id");
    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando metodo generarExpedienteBase] Paciente: %s | Tenant: %s", paciente, tenantId))
      .build());
    int yearActual = LocalDate.now().getYear();
    // Definimos el rango del año actual
    LocalDateTime inicioYear = LocalDateTime.of(yearActual, 1, 1, 0, 0, 0);
    LocalDateTime finYear= LocalDateTime.of(yearActual, 12, 31, 23, 59, 59);
    // Obtenemos el último número para este tenant y año
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
    exp.setFechaCreacion(LocalDateTime.now());
    expedienteRepository.save(exp);
    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[El expediente se guardo en la base de datos exitosamente] Expediente: %s", exp))
      .build());
    return exp;
  }

  @Override
  public boolean tieneHistoriaClinica(Long expedienteId) {
    return false;
  }

  @Override
  public Optional<Expediente> findByPacienteIdAndTenantId(Long pacienteId, String tenantId) {
    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Buscando expediente por PacienteId: %d y Tenant: %s]", pacienteId, tenantId))
      .build());
    return expedienteRepository.findByPacienteIdAndTenantId(pacienteId, tenantId);
  }
}
