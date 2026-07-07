package com.mx.asc.sanus_suite_backend.historia_clinica.services;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.historia_clinica.entities.HistoriaClinica;

public interface HistoriaClinicaService {
  HistoriaClinica obtenerPorExpediente(Expediente expediente, String tenantId);
  HistoriaClinica guardarOActualizar(HistoriaClinica historia, String tenantId);
  HistoriaClinica firmarHistoriaClinica(Long id, Long medicoId, String tenantId);
}
