package com.mx.asc.sanus_suite_backend.historia_clinica.mappers;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.historia_clinica.dtos.HistoriaClinicaRequestDto;
import com.mx.asc.sanus_suite_backend.historia_clinica.dtos.HistoriaClinicaResponseDto;
import com.mx.asc.sanus_suite_backend.historia_clinica.entities.HistoriaClinica;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HistoriaClinicaMapper {

  public HistoriaClinica toEntity(HistoriaClinicaRequestDto dto, Expediente expediente, String tenantId) {
    if (dto == null) {
      return null;
    }

    HistoriaClinica entity = new HistoriaClinica();

    if (dto.getId() != null) {
      entity.setId(dto.getId());
    }

    entity.setExpediente(expediente);
    entity.setTenantId(tenantId);

    // Mapeo directo de textos descriptivos
    entity.setMotivoConsulta(toUpper(dto.getMotivoConsulta()));
    entity.setPadecimientoActual(toUpper(dto.getPadecimientoActual()));
    entity.setAntecedentesHeredofamiliares(toUpper(dto.getAntecedentesHeredofamiliares()));
    entity.setAntecedentesPatologicos(toUpper(dto.getAntecedentesPatologicos()));
    entity.setAntecedentesNoPatologicos(toUpper(dto.getAntecedentesNoPatologicos()));
    entity.setInterrogatorioAparatosSistemas(toUpper(dto.getInterrogatorioAparatosSistemas()));
    entity.setExploracionFisica(toUpper(dto.getExploracionFisica()));
    entity.setDiagnostico(toUpper(dto.getDiagnostico()));
    entity.setPlanTratamiento(toUpper(dto.getPlanTratamiento()));

    // Estado de Firma
    boolean esFirma = Boolean.TRUE.equals(dto.getFirmado());
    entity.setFirmado(esFirma);
    if (esFirma && entity.getFechaFirma() == null) {
      entity.setFechaFirma(LocalDateTime.now());
    }

    if (dto.getFirmadoPorMedicoId() != null) {
      entity.setFirmadoPorMedicoId(dto.getFirmadoPorMedicoId());
    }

    return entity;
  }

  public HistoriaClinicaResponseDto toResponseDto(HistoriaClinica entity) {
    if (entity == null) {
      return null;
    }

    String numExp = (entity.getExpediente() != null) ? entity.getExpediente().getNumeroExpediente() : null;

    return HistoriaClinicaResponseDto.builder()
      .id(entity.getId())
      .numeroExpediente(numExp)
      .motivoConsulta(entity.getMotivoConsulta())
      .padecimientoActual(entity.getPadecimientoActual())
      .antecedentesHeredofamiliares(entity.getAntecedentesHeredofamiliares())
      .antecedentesPatologicos(entity.getAntecedentesPatologicos())
      .antecedentesNoPatologicos(entity.getAntecedentesNoPatologicos())
      .interrogatorioAparatosSistemas(entity.getInterrogatorioAparatosSistemas())
      .exploracionFisica(entity.getExploracionFisica())
      .diagnostico(entity.getDiagnostico())
      .planTratamiento(entity.getPlanTratamiento())
      .firmado(entity.isFirmado())
      .fechaFirma(entity.getFechaFirma())
      .firmadoPorMedicoId(entity.getFirmadoPorMedicoId())
      .build();
  }

  private String toUpper(String str) {
    return str != null ? str.trim().toUpperCase() : null;
  }
}