package com.mx.asc.sanus_suite_backend.nota_evolucion.services;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.nota_evolucion.dtos.NotaEvolucionRequestDto;
import com.mx.asc.sanus_suite_backend.nota_evolucion.entities.NotaEvolucion;

import java.util.List;

public interface NotaEvolucionService {

  NotaEvolucion guardarOActualizar(NotaEvolucion entity, String tenantId, Expediente expediente);

  //NotaEvolucionRequestDto firmarNota(Long id, Long medicoId);

  //NotaEvolucionRequestDto obtenerPorId(Long id);

  //List<NotaEvolucionRequestDto> obtenerHistoricoPorPaciente(Long pacienteId);

  List<NotaEvolucion> obtenerHistoricoPorExpedienteId(Long expedienteId, String tenantId);

  NotaEvolucion actualizarNota(NotaEvolucion entityActualizada, String tenantId);
  NotaEvolucion obtenerPorIdYTenantId(Long id, String tenantId);
  void firmarNotaEvolucion(Medico medico, NotaEvolucion entity, String tenantId);
}
