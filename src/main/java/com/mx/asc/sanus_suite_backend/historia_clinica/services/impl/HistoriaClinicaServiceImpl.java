package com.mx.asc.sanus_suite_backend.historia_clinica.services.impl;

import com.mx.asc.log.bean.LogBean;
import com.mx.asc.log.service.LoggerAscService;
import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.historia_clinica.entities.HistoriaClinica;
import com.mx.asc.sanus_suite_backend.historia_clinica.repositories.HistoriaClinicaRepository;
import com.mx.asc.sanus_suite_backend.historia_clinica.services.HistoriaClinicaService;
import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.util.exceptions.ExceptionGenerica;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HistoriaClinicaServiceImpl implements HistoriaClinicaService {

  private final HistoriaClinicaRepository repository;
  private final LoggerAscService log;

  @Override
  @Transactional(readOnly = true)
  public HistoriaClinica obtenerPorExpediente(Expediente expediente, String tenantId) {

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando método obtenerPorExpediente] Expediente: %s | Tenant: %s",
        expediente.getNumeroExpediente(), tenantId))
      .build());

    return repository.findByExpedienteAndTenantId(expediente, tenantId)
      .orElseThrow(() -> ExceptionGenerica.lanzar404(traceId, "No se encontró historia clínica para esos datos"));
  }

  @Override
  @Transactional
  public HistoriaClinica guardarOActualizar(HistoriaClinica nuevaHistoria, String tenantId) {

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando método guardarOActualizar] Tenant: %s", tenantId))
      .build());

    return repository.findByExpedienteAndTenantId(nuevaHistoria.getExpediente(), tenantId)
      .map(historiaExistente -> {
        if (historiaExistente.isFirmado()) {
          log.warn(LogBean.builder()
            .clase(getClass())
            .message(String.format("[Intento de modificación denegado en historia clínica firmada ID: %d]", historiaExistente.getId()))
            .build());

          throw ExceptionGenerica.lanzar400(traceId, "Modificación denegada: La Historia Clínica ya ha sido firmada legalmente.");
        }

        log.info(LogBean.builder()
          .clase(getClass())
          .message(String.format("[Actualizando historia clínica existente ID: %d]", historiaExistente.getId()))
          .build());

        historiaExistente.setMotivoConsulta(nuevaHistoria.getMotivoConsulta());
        historiaExistente.setPadecimientoActual(nuevaHistoria.getPadecimientoActual());
        historiaExistente.setAntecedentesHeredofamiliares(nuevaHistoria.getAntecedentesHeredofamiliares());
        historiaExistente.setAntecedentesPatologicos(nuevaHistoria.getAntecedentesPatologicos());
        historiaExistente.setAntecedentesNoPatologicos(nuevaHistoria.getAntecedentesNoPatologicos());
        historiaExistente.setInterrogatorioAparatosSistemas(nuevaHistoria.getInterrogatorioAparatosSistemas());
        historiaExistente.setExploracionFisica(nuevaHistoria.getExploracionFisica());
        historiaExistente.setDiagnostico(nuevaHistoria.getDiagnostico());
        historiaExistente.setPlanTratamiento(nuevaHistoria.getPlanTratamiento());

        return repository.save(historiaExistente);
      })
      .orElseGet(() -> {
        log.info(LogBean.builder()
          .clase(getClass())
          .message(String.format("[Creando nueva historia clínica para Expediente ID: %d]",
            nuevaHistoria.getExpediente().getId()))
          .build());

        nuevaHistoria.setTenantId(tenantId);
        return repository.save(nuevaHistoria);
      });
  }

  @Override
  @Transactional
  public HistoriaClinica firmarHistoriaClinica(Long historiaId, Medico medico, String tenantId) {

    String traceId = ThreadContext.get("id");

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Iniciando método firmarHistoriaClinica] HistoriaId: %s | MedicoId: %s  | Tenant: %s",
        historiaId, medico.getId(), tenantId))
      .build());

    HistoriaClinica historia = repository.findByIdAndTenantId(historiaId, tenantId)
      .orElseThrow(() -> ExceptionGenerica.lanzar400(traceId, "Registro clínico no encontrado."));

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Validando que la historia clinica no este firmada] HistoriaId: %s | MedicoId: %s  | Tenant: %s",
        historiaId, medico.getId(), tenantId))
      .build());
    if (historia.isFirmado()) {
      throw ExceptionGenerica.lanzar400(traceId, "El documento clínico ya cuenta con una firma registrada.");
    }

    String nombreCompleto = String.format("%s %s %s",
      medico.getNombre(),
      medico.getPrimerApellido(),
      medico.getSegundoApellido() != null ? medico.getSegundoApellido() : "").trim().toUpperCase();

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Firmando historia clinica] HistoriaId: %s | MedicoId: %s  | Tenant: %s",
        historiaId, medico.getId(), tenantId))
      .build());

    historia.setFirmado(true);
    historia.setFechaFirma(LocalDateTime.now());
    historia.setFirmadoPorMedicoId(medico.getId());
    historia.setMedicoNombreSnapshot(nombreCompleto);
    historia.setMedicoCedulaSnapshot(medico.getCedulaProfesional().toUpperCase());

    log.info(LogBean.builder()
      .clase(getClass())
      .message(String.format("[Historia clinica firmada] HistoriaId: %s | MedicoId: %s  | Tenant: %s",
        historiaId, medico.getId(), tenantId))
      .build());

    return repository.save(historia);
  }
}