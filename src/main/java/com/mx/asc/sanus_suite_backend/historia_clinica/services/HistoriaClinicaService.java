package com.mx.asc.sanus_suite_backend.historia_clinica.services;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.historia_clinica.entities.HistoriaClinica;
import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;

public interface HistoriaClinicaService {
  HistoriaClinica obtenerPorExpediente(Expediente expediente, String tenantId);
  HistoriaClinica guardarOActualizar(HistoriaClinica historia, String tenantId);
  HistoriaClinica firmarHistoriaClinica(Long id, Medico medico, String tenantId);
}
