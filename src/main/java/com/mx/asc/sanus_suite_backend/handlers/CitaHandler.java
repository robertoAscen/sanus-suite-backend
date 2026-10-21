package com.mx.asc.sanus_suite_backend.handlers;

import com.mx.asc.sanus_suite_backend.citas.dtos.CambiarEstadoCitaDto;
import com.mx.asc.sanus_suite_backend.citas.dtos.CitaRequestDto;
import com.mx.asc.sanus_suite_backend.citas.dtos.CitaResponseDto;
import com.mx.asc.sanus_suite_backend.citas.entities.Cita;
import com.mx.asc.sanus_suite_backend.citas.mappers.CitaMapper;
import com.mx.asc.sanus_suite_backend.citas.services.CitaService;
import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.expedientes.services.ExpedienteService;
import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.medicos.services.MedicoService;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.pacientes.services.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CitaHandler {

  private final CitaService citaService;
  private final MedicoService medicoService;
  private final PacienteService pacienteService;
  private final ExpedienteService expedienteService;
  private final CitaMapper citaMapper;

  @Transactional
  public Long agendarCita(CitaRequestDto dto, String tenantId, Long usuarioId) {
    Medico medico = medicoService.obtenerPorUserId(usuarioId, tenantId);
    Expediente expediente = expedienteService.findByPacienteIdAndTenantId(dto.getPacienteId(), tenantId);

    Cita entity = citaMapper.toEntity(dto, medico.getId(), expediente != null ? expediente.getId() : null, tenantId);
    Cita guardada = citaService.guardar(entity);
    return guardada.getId();
  }

  public List<CitaResponseDto> obtenerAgendaMedico(LocalDateTime inicio, LocalDateTime fin, String tenantId, Long usuarioId) {
    Medico medico = medicoService.obtenerPorUserId(usuarioId, tenantId);
    List<Cita> citas = citaService.obtenerCitasPorRangoYMedico(medico.getId(), tenantId, inicio, fin);

    return citas.stream().map(cita -> {
      Paciente paciente = pacienteService.obtenerPacientePorIdAndTenantId(cita.getPacienteId(), tenantId);
      Expediente expediente = cita.getExpedienteId() != null
        ? expedienteService.findByPacienteIdAndTenantId(cita.getPacienteId(), tenantId) : null;
      return citaMapper.toResponseDto(cita, medico, paciente, expediente);
    }).toList();
  }

  @Transactional
  public void cambiarEstado(CambiarEstadoCitaDto dto, String tenantId) {
    citaService.cambiarEstado(dto.getCitaId(), dto.getNuevoEstado(), tenantId);
  }
}