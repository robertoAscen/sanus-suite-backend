package com.mx.asc.sanus_suite_backend.handlers;


import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.expedientes.services.ExpedienteService;
import com.mx.asc.sanus_suite_backend.historia_clinica.entities.HistoriaClinica;
import com.mx.asc.sanus_suite_backend.historia_clinica.services.HistoriaClinicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class HistoriaClinicaHandler {

  private final HistoriaClinicaService historiaClinicaService;
  private final ExpedienteService expedienteService;

  @Transactional(readOnly = true)
  public HistoriaClinica obtenerHistoriaClinicaPorExpedienteYTenantId(String numExpediente, String tenantId){
    Expediente expedienteEncontrado = expedienteService.findByNumeroExpedienteAndTenantId(numExpediente, tenantId);
    return historiaClinicaService.obtenerPorExpediente(expedienteEncontrado, tenantId);
  }

  @Transactional
  public HistoriaClinica guardarOActualizarHistoriaClinica(HistoriaClinica historiaClinica, String tenantId){
    return historiaClinicaService.guardarOActualizar(historiaClinica, tenantId);
  }
}
