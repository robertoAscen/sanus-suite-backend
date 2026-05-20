package com.mx.asc.sanus_suite_backend.expedientes.enums;

public enum TipoNota {
  HISTORIA_CLINICA,    // Nota inicial completa (la más densa)
  EVOLUCION,           // Seguimiento diario o por consulta
  INTERCONSULTA,       // Opinión de otro especialista
  URGENCIAS,           // Nota de ingreso/atención inmediata
  EGRESO,              // Resumen clínico al dar de alta
  PRE_OPERATORIA,      // Requerida antes de cirugías
  POST_OPERATORIA      // Hallazgos y técnica después de cirugía
}
