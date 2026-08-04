package com.mx.asc.sanus_suite_backend.historia_clinica.repositories;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.historia_clinica.entities.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {

  Optional<HistoriaClinica> findByExpedienteAndTenantId(Expediente expediente, String tenantId);
  Optional<HistoriaClinica> findByIdAndTenantId(Long historiaClinicaId, String tenantId);
  Optional<HistoriaClinica> findByExpedienteIdAndTenantId(Long expedienteId, String tenantId);
}
