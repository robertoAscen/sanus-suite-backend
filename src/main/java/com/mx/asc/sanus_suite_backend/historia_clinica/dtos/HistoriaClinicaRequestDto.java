package com.mx.asc.sanus_suite_backend.historia_clinica.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaClinicaRequestDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  @NotBlank(message = "El número de expediente es obligatorio")
  private String numeroExpediente;

  private String motivoConsulta;

  @NotBlank(message = "El padecimiento actual es requerido por norma oficial")
  private String padecimientoActual;

  private String antecedentesHeredofamiliares;
  private String antecedentesPatologicos;
  private String antecedentesNoPatologicos;
  private String interrogatorioAparatosSistemas;
  private String exploracionFisica;
  private String diagnostico;
  private String planTratamiento;

  // Control de Firma / Estado
  private Boolean borrador;
  private Boolean firmado;
  private Long firmadoPorMedicoId;
}