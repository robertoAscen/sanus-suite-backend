package com.mx.asc.sanus_suite_backend.citas.repositories;

import com.mx.asc.sanus_suite_backend.citas.entities.Cita;
import com.mx.asc.sanus_suite_backend.citas.enums.EstadoCitaEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends CrudRepository<Cita, Long> {

  Optional<Cita> findByIdAndTenantId(Long id, String tenantId);

  // Consultar citas por rango de fechas para el calendario/agenda del médico
  List<Cita> findByMedicoIdAndTenantIdAndFechaHoraInicioBetweenOrderByFechaHoraInicioAsc(
    Long medicoId, String tenantId, LocalDateTime inicio, LocalDateTime fin
  );

  // Listar citas de un paciente específico
  List<Cita> findByPacienteIdAndTenantIdOrderByFechaHoraInicioDesc(Long pacienteId, String tenantId);

  // Listar todas las citas del día para el tenant
  List<Cita> findByTenantIdAndFechaHoraInicioBetweenOrderByFechaHoraInicioAsc(
    String tenantId, LocalDateTime inicio, LocalDateTime fin
  );
}