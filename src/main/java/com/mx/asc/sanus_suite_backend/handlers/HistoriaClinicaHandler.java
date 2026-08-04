package com.mx.asc.sanus_suite_backend.handlers;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.expedientes.services.ExpedienteService;
import com.mx.asc.sanus_suite_backend.historia_clinica.dtos.HistoriaClinicaRequestDto;
import com.mx.asc.sanus_suite_backend.historia_clinica.dtos.HistoriaClinicaResponseDto;
import com.mx.asc.sanus_suite_backend.historia_clinica.entities.HistoriaClinica;
import com.mx.asc.sanus_suite_backend.historia_clinica.mappers.HistoriaClinicaMapper;
import com.mx.asc.sanus_suite_backend.historia_clinica.services.HistoriaClinicaService;
import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.medicos.services.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoriaClinicaHandler {

  private final HistoriaClinicaService historiaClinicaService;
  private final ExpedienteService expedienteService;
  private final HistoriaClinicaMapper historiaClinicaMapper;
  private final MedicoService medicoService;

  public HistoriaClinicaResponseDto guardarOActualizar(HistoriaClinicaRequestDto requestDto, String tenantId) {

    Expediente expediente = expedienteService.findByNumeroExpedienteAndTenantId(requestDto.getNumeroExpediente(), tenantId);
    HistoriaClinica entity = historiaClinicaMapper.toEntity(requestDto, expediente, tenantId);
    HistoriaClinica guardada = historiaClinicaService.guardarOActualizar(entity, tenantId);
    return historiaClinicaMapper.toResponseDto(guardada);
  }

  public HistoriaClinicaResponseDto obtenerPorNumeroExpediente(String numeroExpediente, String tenantId) {

    Expediente expediente = expedienteService.findByNumeroExpedienteAndTenantId(numeroExpediente, tenantId);
    HistoriaClinica historia = historiaClinicaService.obtenerPorExpediente(expediente, tenantId);
    return historiaClinicaMapper.toResponseDto(historia);
  }

  public HistoriaClinicaResponseDto firmarHistoriaClinica(Long historiaId, Long userId, String tenantId) {

    Medico medico = medicoService.obtenerPorUserId(userId, tenantId);
    HistoriaClinica historiaFirmada = historiaClinicaService.firmarHistoriaClinica(historiaId, medico, tenantId);
    return historiaClinicaMapper.toResponseDto(historiaFirmada);
  }
}