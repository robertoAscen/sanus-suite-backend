package com.mx.asc.sanus_suite_backend.util.constants;

public class Constantes {

  public static final String V1 = "/v1";
  public static final String API = "/api";
  public static final String LISTAR = "/listar";
  public static final String GUARDAR = "/guardar";
  public static final String BAJA= "/baja";
  public static final String ID = "/{id}";
  public static final String PACIENTES = "/pacientes";
  public static final String UPDATE_PACIENTE = "/update";

  public static final String SISTEMA = "MONO_SANUS_SUITE";
  public static final String SUCCESS_OPERATION = "Operación Exitosa";
  public static final String OPERATION_DUPLICATED = "Paciente existente en la base de datos";
  public static final String BAD_REQUEST = "Petición no válida, favor de validar su información";
  public static final String UNAUTHORIZED = "No estas autorizado, favor de validar";
  public static final String NOT_FOUND = "No se encontró información";

  public static final String INTERNAL_ERROR = "Problemas al procesar su solicitud favor de contactar a su administrador";

  public static final String RECORD_NOT_FOUND = "informacion no encontrada";

  public static final String URL_ERROR = "https://sanus-developer.sanusmed.com.mx/errors#";

  public static final String LOGIN = "/login";
  public static final String AUTH = "/auth";

  public static final String HISTORIAS_CLINICAS = "/historias-clinicas";
  public static final String EXPEDIENTE = "/expediente";
  public static final String NUMERO_EXPEDIENTE = "/{numeroExpediente}";
  public static final String FIRMAR = "/firmar";
  public static final String HEADER_X_TENANT_ID = "x-tenant-id";
  public static final String HEADER_X_USUARIO_ID = "x-usuario-id";


  public Constantes() {
    throw new RuntimeException(getClass().toString());
  }
}
