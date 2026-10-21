package com.mx.asc.sanus_suite_backend.receta_medica.mappers;

import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.receta_medica.dtos.RecetaDetalleRequestDto;
import com.mx.asc.sanus_suite_backend.receta_medica.dtos.RecetaDetalleResponseDto;
import com.mx.asc.sanus_suite_backend.receta_medica.dtos.RecetaMedicaRequestDto;
import com.mx.asc.sanus_suite_backend.receta_medica.dtos.RecetaMedicaResponseDto;
import com.mx.asc.sanus_suite_backend.receta_medica.entities.RecetaDetalle;
import com.mx.asc.sanus_suite_backend.receta_medica.entities.RecetaMedica;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RecetaMedicaMapper {

  // Transformar RequestDto a Entidad RecetaMedica
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "tenantId", source = "tenantId")
  @Mapping(target = "expedienteId", source = "expedienteId")
  @Mapping(target = "medicoId", source = "medicoId")
  @Mapping(target = "pacienteId", source = "dto.pacienteId")
  @Mapping(target = "notaEvolucionId", source = "dto.notaEvolucionId")
  @Mapping(target = "observacionesGenerales", source = "dto.observacionesGenerales")
  @Mapping(target = "detalles", source = "dto.medicamentos")
  @Mapping(target = "firmado", ignore = true)
  @Mapping(target = "fechaEmision", ignore = true)
  @Mapping(target = "fechaFirma", ignore = true)
  @Mapping(target = "medicoNombreSnapshot", ignore = true)
  @Mapping(target = "medicoCedulaSnapshot", ignore = true)
  RecetaMedica toEntity(RecetaMedicaRequestDto dto, Long expedienteId, Long medicoId, String tenantId);

  // Transformar detalle DTO a entidad RecetaDetalle
  @Mapping(target = "id", ignore = true)
  RecetaDetalle toDetalleEntity(RecetaDetalleRequestDto dto);

  // Transformar Entidad a ResponseDto especificando explícitamente el origen de "id" y "medicoId"
  @Mapping(target = "id", source = "entity.id")
  @Mapping(target = "medicoId", source = "medico.id")
  @Mapping(
    target = "pacienteNombre",
    expression = "java(formatearNombreCompleto(paciente != null ? paciente.getNombre() : null, paciente != null ? paciente.getApellidoPaterno() : null, paciente != null ? paciente.getApellidoMaterno() : null))"
  )
  @Mapping(target = "medicamentos", source = "entity.detalles")
  RecetaMedicaResponseDto toResponseDto(RecetaMedica entity, Medico medico, Paciente paciente);

  // Transformar detalle Entidad a ResponseDto
  RecetaDetalleResponseDto toDetalleResponseDto(RecetaDetalle entity);

  List<RecetaDetalle> toDetalleEntityList(List<RecetaDetalleRequestDto> dtos);

  List<RecetaDetalleResponseDto> toDetalleResponseDtoList(List<RecetaDetalle> entities);

  // Helper reusable para formatear nombres sin nulls ni espacios extra
  default String formatearNombreCompleto(String nombre, String primerApellido, String segundoApellido) {
    StringBuilder sb = new StringBuilder();
    if (nombre != null) sb.append(nombre.trim());
    if (primerApellido != null) sb.append(" ").append(primerApellido.trim());
    if (segundoApellido != null) sb.append(" ").append(segundoApellido.trim());
    return !sb.isEmpty() ? sb.toString() : null;
  }
}
