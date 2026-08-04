package com.mx.asc.sanus_suite_backend.medicos.repositories;

import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

  Optional<Medico> findByUserIdAndTenantId(Long userId, String tenantId);
  boolean existsByCedulaProfesionalAndTenantId(String cedulaProfesional, String tenantId);
  boolean existsByUserIdAndTenantId(Long userId, String tenantId);
}