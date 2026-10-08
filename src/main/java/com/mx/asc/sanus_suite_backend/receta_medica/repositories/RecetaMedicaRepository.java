package com.mx.asc.sanus_suite_backend.receta_medica.repositories;

import com.mx.asc.sanus_suite_backend.receta_medica.entities.RecetaMedica;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecetaMedicaRepository extends CrudRepository<RecetaMedica, Long> {

  Optional<RecetaMedica> findByIdAndTenantId(Long id, String tenantId);

  List<RecetaMedica> findByPacienteIdAndTenantIdOrderByFechaEmisionDesc(Long pacienteId, String tenantId);

  List<RecetaMedica> findByExpedienteIdAndTenantIdOrderByFechaEmisionDesc(Long expedienteId, String tenantId);
}
