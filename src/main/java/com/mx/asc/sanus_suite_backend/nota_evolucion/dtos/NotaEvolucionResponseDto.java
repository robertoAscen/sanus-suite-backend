package com.mx.asc.sanus_suite_backend.nota_evolucion.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotaEvolucionResponseDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String numeroExpediente;
  private Long pacienteId;
  private String nombrePaciente;
  private Long medicoId;
  private String nombreMedico;
  private Long historiaClinicaId;
  private LocalDateTime fechaConsulta;
  private String subjetivo;
  private String objetivo;
  private String analisis;
  private String planTratamiento;

  // Signos Vitales
  private String presionArterial;
  private Integer frecuenciaCardiaca;
  private Integer frecuenciaRespiratoria;
  private BigDecimal temperatura;
  private BigDecimal peso;
  private BigDecimal talla;
  private BigDecimal imc;
  private Integer saturacionOxigeno;

  // Estado / Firma Legal
  private Boolean firmado;
  private LocalDateTime fechaFirma;
  private Long firmadoPorMedicoId;
}
