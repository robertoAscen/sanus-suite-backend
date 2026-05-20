package com.mx.asc.sanus_suite_backend.expedientes.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ContenidoNotaConverter implements AttributeConverter<Object, String> {

  private final ObjectMapper objectMapper = new ObjectMapper()
    // Esto es lo que falta: registrar el soporte para fechas
    .registerModule(new JavaTimeModule())
    // Opcional: para que no escriba las fechas como un array de números [2026, 5, 6...]
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  @Override
  public String convertToDatabaseColumn(Object attribute) {
    try {
      return attribute == null ? null : objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error serializando nota", e);
    }
  }

  @Override
  public Object convertToEntityAttribute(String dbData) {
    try {
      if (dbData == null || dbData.isEmpty()) return null;
      return objectMapper.readValue(dbData, Object.class);
    } catch (Exception e) {
      throw new RuntimeException("Error deserializando nota", e);
    }
  }
}
