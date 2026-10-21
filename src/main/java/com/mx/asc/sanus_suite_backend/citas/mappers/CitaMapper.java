package com.mx.asc.sanus_suite_backend.citas.mappers;

import com.mx.asc.sanus_suite_backend.citas.dtos.CitaRequestDto;
import com.mx.asc.sanus_suite_backend.citas.dtos.CitaResponseDto;
import com.mx.asc.sanus_suite_backend.citas.entities.Cita;
import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CitaMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "tenantId", source = "tenantId")
  @Mapping(target = "medicoId", source = "medicoId")
  @Mapping(target = "expedienteId", source = "expedienteId")
  @Mapping(target = "estado", expression = "java(com.mx.asc.sanus_suite_backend.citas.enums.EstadoCitaEnum.PROGRAMADA)")
  @Mapping(target = "notaEvolucionId", ignore = true)
  Cita toEntity(CitaRequestDto dto, Long medicoId, Long expedienteId, String tenantId);

  @Mapping(target = "id", source = "entity.id")
  @Mapping(target = "medicoId", source = "medico.id")
  @Mapping(target = "medicoNombre", expression = "java(formatearNombreMedico(medico))")
  @Mapping(target = "pacienteId", source = "paciente.id")
  @Mapping(target = "pacienteNombre", expression = "java(formatearNombrePaciente(paciente))")
  @Mapping(target = "pacienteTelefono", source = "paciente.telefono")
  @Mapping(target = "expedienteId", source = "expediente.id")
  @Mapping(target = "numeroExpediente", source = "expediente.numeroExpediente")
  CitaResponseDto toResponseDto(Cita entity, Medico medico, Paciente paciente, Expediente expediente);

  default String formatearNombreMedico(Medico m) {
    if (m == null) return null;
    return String.format("Dr(a). %s %s", m.getNombre(), m.getPrimerApellido()).trim();
  }

  default String formatearNombrePaciente(Paciente p) {
    if (p == null) return null;
    return String.format("%s %s %s", p.getNombre(), p.getApellidoPaterno(), p.getApellidoMaterno()).trim();
  }
}