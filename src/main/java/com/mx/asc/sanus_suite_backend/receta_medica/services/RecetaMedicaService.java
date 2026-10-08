package com.mx.asc.sanus_suite_backend.receta_medica.services;

import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.receta_medica.entities.RecetaMedica;

public interface RecetaMedicaService {

  RecetaMedica guardar(RecetaMedica entity, String tenantId);
  RecetaMedica obtenerPorIdYTenantId(Long id, String tenantId);
  void firmarReceta(Medico medico, RecetaMedica entity, String tenantId);

}
