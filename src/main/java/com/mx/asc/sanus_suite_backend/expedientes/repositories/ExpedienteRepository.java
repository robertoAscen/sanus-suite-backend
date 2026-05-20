package com.mx.asc.sanus_suite_backend.expedientes.repositories;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {

  @Query("SELECT COUNT(e) FROM Expediente e " +
    "WHERE e.tenantId = :tenantId " +
    "AND e.fechaCreacion >= :inicio " +
    "AND e.fechaCreacion <= :fin")
  Long countByTenantIdAndFechas(
    @Param("tenantId") String tenantId,
    @Param("inicio") LocalDateTime inicio,
    @Param("fin") LocalDateTime fin
  );

  @Query("SELECT e FROM Expediente e WHERE e.paciente.id = :pacienteId AND e.tenantId = :tenantId")
  Optional<Expediente> buscarPorPacienteYTenant(@Param("pacienteId") Long pacienteId, @Param("tenantId") String tenantId);


  Optional<Expediente> findByPacienteIdAndTenantId(Long pacienteId, String tenantId);
}