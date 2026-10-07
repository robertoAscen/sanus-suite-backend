package com.mx.asc.sanus_suite_backend.receta_medica.services.impl;

import com.mx.asc.log.bean.LogBean;
import com.mx.asc.log.service.LoggerAscService;
import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.receta_medica.entities.RecetaMedica;
import com.mx.asc.sanus_suite_backend.receta_medica.repositories.RecetaMedicaRepository;
import com.mx.asc.sanus_suite_backend.receta_medica.services.RecetaMedicaService;
import com.mx.asc.sanus_suite_backend.util.exceptions.ExceptionGenerica;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RecetaMedicaServiceImpl implements RecetaMedicaService {

  private final RecetaMedicaRepository repository;
  private final LoggerAscService log;

  @Override
  @Transactional
  public RecetaMedica guardar(RecetaMedica entity, String tenantId) {
    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando método guardar Receta] Tenant: %s", tenantId))
      .build());

    if (entity.getFechaEmision() == null) {
      entity.setFechaEmision(LocalDateTime.now());
    }

    return repository.save(entity);
  }

  @Override
  public RecetaMedica obtenerPorIdYTenantId(Long id, String tenantId) {
    String traceId = ThreadContext.get("id");

    return repository.findByIdAndTenantId(id, tenantId)
      .orElseThrow(() -> ExceptionGenerica.lanzar404(traceId, "La receta médica solicitada no existe."));
  }

  @Override
  public void firmarReceta(Medico medico, RecetaMedica entity, String tenantId) {
    if (entity == null || medico == null) return;
    entity.asentarFirmaLegal(medico);
  }
}