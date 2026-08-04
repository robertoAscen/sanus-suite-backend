package com.mx.asc.sanus_suite_backend.medicos.services.impl;

import com.mx.asc.log.bean.LogBean;
import com.mx.asc.log.service.LoggerAscService;
import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.medicos.repositories.MedicoRepository;
import com.mx.asc.sanus_suite_backend.medicos.services.MedicoService;
import com.mx.asc.sanus_suite_backend.util.exceptions.ExceptionGenerica;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicoServiceImpl implements MedicoService {

  private final MedicoRepository repository;
  private final LoggerAscService log;

  @Override
  @Transactional(readOnly = true)
  public Medico obtenerPorUserId(Long userId, String tenantId) {

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Buscando perfil médico para User ID: %d | Tenant: %s]", userId, tenantId))
      .build());

    return repository.findByUserIdAndTenantId(userId, tenantId)
      .orElseThrow(() -> ExceptionGenerica.lanzar400(traceId, "El usuario firmado no cuenta con un perfil médico registrado con cédula profesional."));
  }

  @Override
  @Transactional
  public Medico guardarOActualizar(Medico medico, String tenantId) {

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Guardando/Actualizando perfil médico para User ID: %d | Tenant: %s]",
        medico.getUser().getId(), tenantId))
      .build());

    if (medico.getId() == null && repository.existsByCedulaProfesionalAndTenantId(medico.getCedulaProfesional(), tenantId)) {
      throw ExceptionGenerica.lanzar400(traceId, "Ya existe un médico registrado con la cédula profesional ingresada.");
    }

    medico.setTenantId(tenantId);
    return repository.save(medico);
  }
}
