package com.mx.asc.sanus_suite_backend.pacientes.repositories;

import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {


  // Solo lista los pacientes que pertenecen a la clínica activa
  List<Paciente> findAllByTenantId(String tenantId);

  // Busca un paciente específico asegurándose de que pertenezca al médico que consulta
  Optional<Paciente> findByIdAndTenantId(Long id, String tenantId);

  boolean existsByCurpAndTenantId(String curp, String tenantId);

  boolean existsByIdAndTenantId(Long id, String tenentId);
}