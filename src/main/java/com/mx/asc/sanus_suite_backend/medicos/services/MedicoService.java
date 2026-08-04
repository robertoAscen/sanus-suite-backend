package com.mx.asc.sanus_suite_backend.medicos.services;

import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;

public interface MedicoService {

  Medico obtenerPorUserId(Long userId, String tenantId);
  Medico guardarOActualizar(Medico medico, String tenantId);
}