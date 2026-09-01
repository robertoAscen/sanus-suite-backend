package com.mx.asc.sanus_suite_backend.nota_evolucion.services.impl;

import com.mx.asc.log.bean.LogBean;
import com.mx.asc.log.service.LoggerAscService;
import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.nota_evolucion.entities.NotaEvolucion;
import com.mx.asc.sanus_suite_backend.nota_evolucion.repositories.NotaEvolucionRepository;
import com.mx.asc.sanus_suite_backend.nota_evolucion.services.NotaEvolucionService;
import com.mx.asc.sanus_suite_backend.util.exceptions.ExceptionGenerica;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotaEvolucionServiceImpl implements NotaEvolucionService {

  private final NotaEvolucionRepository repository;
  private final LoggerAscService log;

  @Override
  @Transactional
  public NotaEvolucion guardarOActualizar(NotaEvolucion entity, String tenantId, Expediente expediente) {

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando método guardarOActualizar] Tenant: %s", tenantId))
      .build());

    if (entity.getId() != null) {
      NotaEvolucion existente = repository.findByIdAndTenantId(entity.getId(), tenantId)
        .orElseThrow(() -> ExceptionGenerica.lanzar404(traceId, "Nota de evolución no encontrada."));

      // Regla NOM-004: Registro ya firmado previamente es inmutable
      if (existente.isFirmado()) {
        throw ExceptionGenerica.lanzar400(traceId, "No es posible modificar una nota de evolución que ya ha sido firmada.");
      }
    }

    // Si no venía firmada explícitamente, asegurar que sea false por defecto
    if (entity.getFechaFirma() == null && !entity.isFirmado()) {
      entity.setFirmado(false);
    }

    return repository.save(entity);
  }

  @Override
  public List<NotaEvolucion> obtenerHistoricoPorExpedienteId(Long expedienteId, String tenantId) {

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando método listar Notas evolucion] ExpedienteId: %s | Tenant: %s", expedienteId, tenantId))
      .build());

    List<NotaEvolucion> notasEvolucion = (List<NotaEvolucion>) repository.findByExpedienteIdAndTenantIdOrderByFechaConsultaDesc(expedienteId, tenantId);

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Notas evolucion encontradas] Notas: %s", notasEvolucion))
      .build());

    return notasEvolucion;
  }

  @Override
  @Transactional
  public NotaEvolucion actualizarNota(NotaEvolucion entityActualizada, String tenantId) {

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando método actualizarNota] Tenant: %s", tenantId))
      .build());

    repository.findByIdAndTenantId(entityActualizada.getId(), tenantId)
      .orElseThrow(() -> ExceptionGenerica.lanzar404(traceId, "Nota de evolución no encontrada."));

    return repository.save(entityActualizada);
  }

  @Override
  public NotaEvolucion obtenerPorIdYTenantId(Long id, String tenantId) {
    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando método obtenerPorIdYTenantId] ID: %s | Tenant: %s", id, tenantId))
      .build());

    NotaEvolucion notaEvolucion = repository.findByIdAndTenantId(id, tenantId)
      .orElseThrow(() -> ExceptionGenerica.lanzar404(traceId, "La nota de evolución solicitada no existe."));

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Nota de evolucion encontrada] Nota: %s", notaEvolucion))
      .build());
    return notaEvolucion;
  }

  @Override
  public void firmarNotaEvolucion(Medico medico, NotaEvolucion entity, String tenantId) {
    if (entity == null || medico == null) return;

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando método firmarNotaEvolucion] medicoId: %s | notaId: %s | Tenant: %s",
        medico.getId(), entity.getId(), tenantId))
      .build());

    // Solo validamos existencia en BD si la nota ya tenía una identidad asignada (actualización)
    if (entity.getId() != null) {
      repository.findByIdAndTenantId(entity.getId(), tenantId)
        .orElseThrow(() -> ExceptionGenerica.lanzar404(traceId, "La nota de evolución solicitada no existe."));
    }

    entity.setFirmado(true);
    entity.setFechaFirma(LocalDateTime.now());
    entity.setFirmadoPorMedicoId(medico.getId());

    String nombreCompletoMedico = String.format("%s %s %s",
      medico.getNombre() != null ? medico.getNombre() : "",
      medico.getPrimerApellido() != null ? medico.getPrimerApellido() : "",
      medico.getSegundoApellido() != null ? medico.getSegundoApellido() : "").trim();

    entity.setMedicoNombreSnapshot(nombreCompletoMedico);
    entity.setMedicoCedulaSnapshot(medico.getCedulaProfesional());
  }
}