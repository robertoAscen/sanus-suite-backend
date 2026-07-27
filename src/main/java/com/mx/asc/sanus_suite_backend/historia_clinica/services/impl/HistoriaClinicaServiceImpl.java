package com.mx.asc.sanus_suite_backend.historia_clinica.services.impl;

import com.mx.asc.log.bean.LogBean;
import com.mx.asc.log.service.LoggerAscService;
import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.expedientes.services.ExpedienteService;
import com.mx.asc.sanus_suite_backend.historia_clinica.entities.HistoriaClinica;
import com.mx.asc.sanus_suite_backend.historia_clinica.repositories.HistoriaClinicaRepository;
import com.mx.asc.sanus_suite_backend.historia_clinica.services.HistoriaClinicaService;
import com.mx.asc.sanus_suite_backend.util.exceptions.ExceptionGenerica;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class HistoriaClinicaServiceImpl implements HistoriaClinicaService {

  private final HistoriaClinicaRepository repository;
  private final LoggerAscService log;

  @Override
  @Transactional(readOnly = true)
  public HistoriaClinica obtenerPorExpediente(Expediente expediente, String tenantId) {

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando metodo obtenerPorExpediente] Expediente: %s | Tenant: %s ", expediente.getNumeroExpediente(), tenantId))
      .build());

    return repository.findByExpedienteAndTenantId(
      expediente, tenantId).orElseThrow(
      () -> ExceptionGenerica.lanzar404(traceId, "No se encontro historia clinica para esos datos"));
  }

  @Override
  @Transactional
  public HistoriaClinica guardarOActualizar(HistoriaClinica historia, String tenantId) {

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando metodo guardarOActualizar] Historia clinica: %s | Tenant: %s ", historia.getId(), tenantId))
      .build());

    // Si ya existe un ID, verificamos que no esté bloqueada por firma legal
    if (historia.getId() != null) {
      log.info(LogBean.builder()
        .clase(getClass()).message(String.format("[validando la existencia de la HC: ] %s", historia.getId())).build());
      HistoriaClinica existente = repository.findById(historia.getId())
        .orElseThrow(() -> new RuntimeException("No existe el registro a actualizar."));

      if (existente.isFirmado()) {
        throw new IllegalStateException("Modificación denegada: La Historia Clínica ya ha sido firmada legalmente.");
      }
    }

    historia.setTenantId(tenantId);
    return repository.save(historia);
  }

  @Override
  @Transactional
  public HistoriaClinica firmarHistoriaClinica(Long id, Long medicoId, String tenantId) {
    HistoriaClinica historia = repository.findById(id)
      .orElseThrow(() -> new RuntimeException("Registro clínico no encontrado."));

    if (!historia.getTenantId().equals(tenantId)) {
      throw new RuntimeException("Acceso no autorizado al recurso clínico solicitado.");
    }

    if (historia.isFirmado()) {
      throw new IllegalStateException("El documento clínico ya cuenta con una firma registrada.");
    }

    // Sellamos el registro clínicamente
    historia.setFirmado(true);
    historia.setFechaFirma(LocalDateTime.now());
    historia.setFirmadoPorMedicoId(medicoId);

    return repository.save(historia);
  }
}