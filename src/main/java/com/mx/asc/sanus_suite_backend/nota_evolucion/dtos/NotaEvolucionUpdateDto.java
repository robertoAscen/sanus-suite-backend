package com.mx.asc.sanus_suite_backend.nota_evolucion.dtos;

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
public class NotaEvolucionUpdateDto implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull(message = "El ID de la nota de evolución es obligatorio para actualizar")
  private Long id;

  private LocalDateTime fechaConsulta;

  // SOAP
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

  // Control de Firma
  private Boolean firmado;
}