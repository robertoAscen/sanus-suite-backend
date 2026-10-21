package com.mx.asc.sanus_suite_backend.citas.services.impl;

import com.mx.asc.sanus_suite_backend.citas.entities.Cita;
import com.mx.asc.sanus_suite_backend.citas.enums.EstadoCitaEnum;
import com.mx.asc.sanus_suite_backend.citas.repositories.CitaRepository;
import com.mx.asc.sanus_suite_backend.citas.services.CitaService;
import com.mx.asc.sanus_suite_backend.util.exceptions.ExceptionGenerica;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

  private final CitaRepository citaRepository;

  @Override
  @Transactional
  public Cita guardar(Cita entity) {
    return citaRepository.save(entity);
  }

  @Override
  public Cita obtenerPorIdYTenantId(Long id, String tenantId) {
    String traceId = ThreadContext.get("id");
    return citaRepository.findByIdAndTenantId(id, tenantId)
      .orElseThrow(() -> ExceptionGenerica.lanzar404(traceId, "La cita médica no existe."));
  }

  @Override
  @Transactional
  public Cita cambiarEstado(Long id, EstadoCitaEnum nuevoEstado, String tenantId) {
    Cita cita = obtenerPorIdYTenantId(id, tenantId);
    cita.setEstado(nuevoEstado);
    return citaRepository.save(cita);
  }

  @Override
  public List<Cita> obtenerCitasPorRangoYMedico(Long medicoId, String tenantId, LocalDateTime inicio, LocalDateTime fin) {
    return citaRepository.findByMedicoIdAndTenantIdAndFechaHoraInicioBetweenOrderByFechaHoraInicioAsc(medicoId, tenantId, inicio, fin);
  }

  @Override
  public List<Cita> obtenerCitasPorPaciente(Long pacienteId, String tenantId) {
    return citaRepository.findByPacienteIdAndTenantIdOrderByFechaHoraInicioDesc(pacienteId, tenantId);
  }
}