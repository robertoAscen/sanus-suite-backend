package com.mx.asc.sanus_suite_backend.handlers;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.expedientes.services.ExpedienteService;
import com.mx.asc.sanus_suite_backend.historia_clinica.entities.HistoriaClinica;
import com.mx.asc.sanus_suite_backend.historia_clinica.services.HistoriaClinicaService;
import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.medicos.services.MedicoService;
import com.mx.asc.sanus_suite_backend.nota_evolucion.dtos.NotaEvolucionRequestDto;
import com.mx.asc.sanus_suite_backend.nota_evolucion.dtos.NotaEvolucionResponseDto;
import com.mx.asc.sanus_suite_backend.nota_evolucion.dtos.NotaEvolucionUpdateDto;
import com.mx.asc.sanus_suite_backend.nota_evolucion.entities.NotaEvolucion;
import com.mx.asc.sanus_suite_backend.nota_evolucion.mappers.NotaEvolucionMapper;
import com.mx.asc.sanus_suite_backend.nota_evolucion.services.NotaEvolucionService;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.pacientes.services.PacienteService;
import com.mx.asc.sanus_suite_backend.util.exceptions.ExceptionGenerica;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotaEvolucionHandler {

  private final NotaEvolucionService notaEvolucionService;
  private final ExpedienteService expedienteService;
  private final MedicoService medicoService;
  private final HistoriaClinicaService historiaClinicaService;
  private final NotaEvolucionMapper notaEvolucionMapper;
  private final PacienteService pacienteService;


  public void guardarOActualizar(NotaEvolucionRequestDto dto, String tenantId, Long usuarioId) {

    Medico medico = medicoService.obtenerPorUserId(usuarioId, tenantId);
    Expediente expediente = expedienteService.findByPacienteIdAndTenantId(dto.getPacienteId(), tenantId);
    HistoriaClinica historiaClinica = historiaClinicaService.obtenerPorExpediente(expediente, tenantId);
    dto.setMedicoId(medico.getId());
    dto.setHistoriaClinicaId(historiaClinica.getId());
    NotaEvolucion entity = notaEvolucionMapper.toEntity(dto, expediente, tenantId);
    procesarFirmaSiEsRequerido(dto.getFirmado(), entity, medico, tenantId);
    notaEvolucionService.guardarOActualizar(entity, tenantId, expediente);
  }

  public List<NotaEvolucionResponseDto> notasEvolucionList(String numExpediente, String tenantId, Long usuarioId){

    Expediente expediente = expedienteService.findByNumeroExpedienteAndTenantId(numExpediente, tenantId);
    Medico medico = medicoService.obtenerPorUserId(usuarioId, tenantId);
    List<NotaEvolucion> notasEvolucion = notaEvolucionService.obtenerHistoricoPorExpedienteId(expediente.getId(), tenantId);
    return notasEvolucion.stream()
      .map(nota -> notaEvolucionMapper.toResponseDto(nota, expediente, expediente.getPaciente(), medico))
      .toList();
  }

  @Transactional
  public Long actualizar(NotaEvolucionUpdateDto dto, String tenantId, Long usuarioId) {

    String traceId = ThreadContext.get("id");
    // 1. Buscar la nota existente en la BD
    NotaEvolucion notaExistente = notaEvolucionService.obtenerPorIdYTenantId(dto.getId(), tenantId);
    // 2. Regla NOM-004: Validar que no estuviera firmada PREVIAMENTE en base de datos
    if (notaExistente.isFirmado()) {
      throw ExceptionGenerica.lanzar400(traceId, "No es posible editar una nota de evolución que ya ha sido firmada.");
    }
    // 3. Mapear cambios
    notaEvolucionMapper.updateEntityFromDto(dto, notaExistente);
    // 4. Aplicar firma si el médico decidió asentar en este momento
    if (Boolean.TRUE.equals(dto.getFirmado())) {
      Medico medico = medicoService.obtenerPorUserId(usuarioId, tenantId);
      notaEvolucionService.firmarNotaEvolucion(medico, notaExistente, tenantId);
    }
    // 5. Guardar cambios
    NotaEvolucion notaGuardada = notaEvolucionService.actualizarNota(notaExistente, tenantId);
    return notaGuardada.getId();
  }

  public NotaEvolucionResponseDto obtenerNotaPorIdYTenantId(Long id, String tenantId, Long usuarioId){

    NotaEvolucion notaEvolucion = notaEvolucionService.obtenerPorIdYTenantId(id, tenantId);
    Paciente paciente = pacienteService.obtenerPacientePorIdAndTenantId(notaEvolucion.getPacienteId(), tenantId);
    Expediente expediente = expedienteService.findByPacienteIdAndTenantId(notaEvolucion.getPacienteId(), tenantId);
    Medico medico = medicoService.obtenerPorUserId(usuarioId, tenantId);
    return notaEvolucionMapper.toResponseDto(notaEvolucion, expediente, paciente, medico);
  }

  private void procesarFirmaSiEsRequerido(Boolean firmado, NotaEvolucion entity, Medico medico, String tenantId) {
    if (Boolean.TRUE.equals(firmado)) {
      notaEvolucionService.firmarNotaEvolucion(medico, entity, tenantId);
    }
  }

}
