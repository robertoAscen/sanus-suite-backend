package com.mx.asc.sanus_suite_backend.receta_medica.mappers;

import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
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
  @Mapping(target = "medicamentos", source = "entity.detalles")
  RecetaMedicaResponseDto toResponseDto(RecetaMedica entity, Medico medico);

  // Transformar detalle Entidad a ResponseDto
  RecetaDetalleResponseDto toDetalleResponseDto(RecetaDetalle entity);

  List<RecetaDetalle> toDetalleEntityList(List<RecetaDetalleRequestDto> dtos);

  List<RecetaDetalleResponseDto> toDetalleResponseDtoList(List<RecetaDetalle> entities);
}
