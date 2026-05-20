package com.mx.asc.sanus_suite_backend.pacientes.mappers;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.pacientes.dtos.PacienteDto;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

  private final ModelMapper modelMapper;

  public PacienteMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  /**
   * Transforma un Paciente y su Expediente en un PacienteDto completo.
   */
  public PacienteDto toDto(Paciente paciente, Expediente expediente) {
    if (paciente == null) return null;

    PacienteDto dto = modelMapper.map(paciente, PacienteDto.class);
    if (expediente != null) {
      dto.setNumeroExpediente(expediente.getNumeroExpediente());
    }
    return dto;
  }

  /**
   * Transforma un Paciente puro a DTO (útil si el expediente se maneja aparte o es opcional).
   */
  public PacienteDto toDto(Paciente paciente) {
    if (paciente == null) return null;
    return modelMapper.map(paciente, PacienteDto.class);
  }
}
