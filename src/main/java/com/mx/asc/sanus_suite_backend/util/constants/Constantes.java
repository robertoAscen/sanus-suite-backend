package com.mx.asc.sanus_suite_backend.util.constants;

public class Constantes {

  public static final String V1 = "/v1";
  public static final String API = "/api";
  public static final String LISTAR_PACIENTES = "/listar-pacientes";
  public static final String ALTA_PACIENTE = "/alta-paciente";
  public static final String BAJA_PACIENTE = "/baja-paciente";
  public static final String ID = "/{id}";
  public static final String PACIENTES = "/pacientes";
  public static final String UPDATE_PACIENTE = "/update-paciente";

  public static final String SISTEMA = "MONO_SANUS_SUITE";
  public static final String SUCCESS_OPERATION = "Operación Exitosa";
  public static final String OPERATION_DUPLICATED = "Paciente existente en la base de datos";
  public static final String BAD_REQUEST = "Petición no válida, favor de validar su información";
  public static final String UNAUTHORIZED = "No estas autorizado, favor de validar";
  public static final String NOT_FOUND = "No se encontró información";

  public static final String INTERNAL_ERROR = "Problemas al procesar su solicitud favor de contactar a su administrador";

  public static final String RECORD_NOT_FOUND = "informacion no encontrada";

  public static final String URL_ERROR = "https://sanus-developer.sanusmed.com.mx/errors#";

  public Constantes() {
    throw new RuntimeException(getClass().toString());
  }
}
