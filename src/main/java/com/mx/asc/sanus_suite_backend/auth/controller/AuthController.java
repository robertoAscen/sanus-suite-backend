package com.mx.asc.sanus_suite_backend.auth.controller;

import com.mx.asc.sanus_suite_backend.auth.dtos.AuthResponseDto;
import com.mx.asc.sanus_suite_backend.auth.dtos.LoginRequestDto; // DTO con username y password
import com.mx.asc.sanus_suite_backend.auth.dtos.UserRegisterDto;
import com.mx.asc.sanus_suite_backend.auth.services.AuthService;
import com.mx.asc.sanus_suite_backend.util.constants.Constantes;
import com.mx.asc.sanus_suite_backend.util.enums.CodigosResponse;
import com.mx.asc.sanus_suite_backend.util.responses.RespuestaApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constantes.API+Constantes.V1+Constantes.AUTH)
@RequiredArgsConstructor
//@CrossOrigin(origins = "*") // Ajustar después a tus dominios permitidos en producción
public class AuthController {

  private final AuthService authService;

  @PostMapping(Constantes.LOGIN)
  public ResponseEntity<RespuestaApi<AuthResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
    AuthResponseDto authData = authService.authenticate(request);
    String traceId = ThreadContext.get("id");
    return RespuestaApi.buildResponse(traceId, Constantes.SUCCESS_OPERATION, authData, CodigosResponse.CODIGO_200);
  }

  @PostMapping("/register")
  public ResponseEntity<RespuestaApi<Void>> register(@Valid @RequestBody UserRegisterDto dto) {
    authService.register(dto);
    String traceId = ThreadContext.get("id");
    return RespuestaApi.buildResponse(traceId, Constantes.SUCCESS_OPERATION, null, CodigosResponse.CODIGO_201);
  }
}