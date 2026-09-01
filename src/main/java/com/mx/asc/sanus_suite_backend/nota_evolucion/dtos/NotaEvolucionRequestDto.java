package com.mx.asc.sanus_suite_backend.nota_evolucion.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotaEvolucionRequestDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  @NotBlank(message = "El número de expediente es obligatorio")
  private String numeroExpediente;

  @NotNull(message = "El ID del paciente es obligatorio")
  private Long pacienteId;

  @NotNull(message = "El ID del médico es obligatorio")
  private Long medicoId;

  private Long historiaClinicaId;

  private LocalDateTime fechaConsulta;

  // SOAP
  @NotNull(message = "El campo Subjetivo (S) es obligatorio")
  private String subjetivo;

  @NotNull(message = "El campo Objetivo (O) es obligatorio")
  private String objetivo;

  @NotNull(message = "El campo Análisis (A) es obligatorio")
  private String analisis;

  @NotNull(message = "El campo Plan (P) es obligatorio")
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

  // Control de Firma / Estado
  private Boolean borrador;
  private Boolean firmado;
  private Long firmadoPorMedicoId;
}