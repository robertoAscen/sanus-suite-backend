package com.mx.asc.sanus_suite_backend.historia_clinica.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaClinicaResponseDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String numeroExpediente;

  private String motivoConsulta;
  private String padecimientoActual;
  private String antecedentesHeredofamiliares;
  private String antecedentesPatologicos;
  private String antecedentesNoPatologicos;
  private String interrogatorioAparatosSistemas;
  private String exploracionFisica;
  private String diagnostico;
  private String planTratamiento;

  // Estado / Firma Legal
  private Boolean firmado;
  private LocalDateTime fechaFirma;
  private Long firmadoPorMedicoId;
}