package com.mx.asc.sanus_suite_backend.nota_evolucion.repositories;

import com.mx.asc.sanus_suite_backend.nota_evolucion.entities.NotaEvolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotaEvolucionRepository extends JpaRepository<NotaEvolucion, Long> {

  Optional<NotaEvolucion> findByIdAndTenantId(Long id, String tenantId);

  // Para el histórico del expediente (ordenadas de más reciente a más antigua)
  List<NotaEvolucion> findByPacienteIdOrderByFechaConsultaDesc(Long pacienteId);

  List<NotaEvolucion> findByExpedienteIdAndTenantIdOrderByFechaConsultaDesc(Long expedienteId, String tenantId);

  // Obtener la última nota firmada para precargar datos/antecedentes si se requiere
  Optional<NotaEvolucion> findFirstByPacienteIdAndFirmadoTrueOrderByFechaConsultaDesc(Long pacienteId);
}