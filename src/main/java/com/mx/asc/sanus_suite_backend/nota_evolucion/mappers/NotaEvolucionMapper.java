package com.mx.asc.sanus_suite_backend.nota_evolucion.mappers;

import com.mx.asc.sanus_suite_backend.expedientes.entities.Expediente;
import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.nota_evolucion.dtos.NotaEvolucionRequestDto;
import com.mx.asc.sanus_suite_backend.nota_evolucion.dtos.NotaEvolucionResponseDto;
import com.mx.asc.sanus_suite_backend.nota_evolucion.dtos.NotaEvolucionUpdateDto;
import com.mx.asc.sanus_suite_backend.nota_evolucion.entities.NotaEvolucion;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface NotaEvolucionMapper {

  // Entity to ResponseDTO (inyeccion cruzada de Expediente)
  @Mapping(source = "expediente.numeroExpediente", target = "numeroExpediente")
  @Mapping(source = "entity.id", target = "id")
  @Mapping(source = "entity.firmado", target = "firmado")
  @Mapping(source = "entity.fechaFirma", target = "fechaFirma")
  @Mapping(source = "entity.firmadoPorMedicoId", target = "firmadoPorMedicoId")
  NotaEvolucionResponseDto toResponseDto(NotaEvolucion entity, Expediente expediente);

  @Mapping(source = "expediente.numeroExpediente", target = "numeroExpediente")
  @Mapping(
    target = "nombrePaciente",
    expression = "java(formatearNombreCompleto(paciente != null ? paciente.getNombre() : null, paciente != null ? paciente.getApellidoPaterno() : null, paciente != null ? paciente.getApellidoMaterno() : null))"
  )
  @Mapping(
    target = "nombreMedico",
    expression = "java(formatearNombreCompleto(medico != null ? medico.getNombre() : null, medico != null ? medico.getPrimerApellido() : null, medico != null ? medico.getSegundoApellido() : null))"
  )
  @Mapping(source = "entity.id", target = "id")
  NotaEvolucionResponseDto toResponseDto(NotaEvolucion entity, Expediente expediente, Paciente paciente, Medico medico);

  // RequestDTO to Entity
  @Mapping(target = "id", source = "dto.id")
  @Mapping(target = "expedienteId", source = "expediente.id")
  @Mapping(target = "tenantId", source = "tenantId")
  @Mapping(target = "firmado", source = "dto.firmado")
  @Mapping(target = "firmadoPorMedicoId", source = "dto.firmadoPorMedicoId")
  @Mapping(target = "subjetivo", source = "dto.subjetivo", qualifiedByName = "toUpper")
  @Mapping(target = "objetivo", source = "dto.objetivo", qualifiedByName = "toUpper")
  @Mapping(target = "analisis", source = "dto.analisis", qualifiedByName = "toUpper")
  @Mapping(target = "planTratamiento", source = "dto.planTratamiento", qualifiedByName = "toUpper")
  @Mapping(target = "presionArterial", source = "dto.presionArterial", qualifiedByName = "toUpper")
  NotaEvolucion toEntity(NotaEvolucionRequestDto dto, Expediente expediente, String tenantId);

  // Actualiza la entidad persistida ignorando los campos nulos del DTO
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "expedienteId", ignore = true)
  @Mapping(target = "pacienteId", ignore = true)
  @Mapping(target = "medicoId", ignore = true)
  @Mapping(target = "historiaClinicaId", ignore = true)
  @Mapping(target = "tenantId", ignore = true)
  @Mapping(target = "firmado", ignore = true) // El firmado se gestiona en la lógica de negocio
  @Mapping(target = "subjetivo", source = "dto.subjetivo", qualifiedByName = "toUpper")
  @Mapping(target = "objetivo", source = "dto.objetivo", qualifiedByName = "toUpper")
  @Mapping(target = "analisis", source = "dto.analisis", qualifiedByName = "toUpper")
  @Mapping(target = "planTratamiento", source = "dto.planTratamiento", qualifiedByName = "toUpper")
  @Mapping(target = "presionArterial", source = "dto.presionArterial", qualifiedByName = "toUpper")
  void updateEntityFromDto(NotaEvolucionUpdateDto dto, @MappingTarget NotaEvolucion entity);

  // Métodos custom helpers (transformación a mayúsculas)
  @Named("toUpper")
  default String toUpper(String str) {
    return str != null ? str.trim().toUpperCase() : null;
  }

  // Helper reusable para formatear nombres sin espacios extra o "null"
  default String formatearNombreCompleto(String nombre, String primerApellido, String segundoApellido) {
    StringBuilder sb = new StringBuilder();
    if (nombre != null) sb.append(nombre.trim());
    if (primerApellido != null) sb.append(" ").append(primerApellido.trim());
    if (segundoApellido != null) sb.append(" ").append(segundoApellido.trim());
    return !sb.isEmpty() ? sb.toString() : null;
  }
}