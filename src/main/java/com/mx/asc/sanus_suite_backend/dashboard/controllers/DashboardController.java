package com.mx.asc.sanus_suite_backend.dashboard.controllers;

import com.mx.asc.sanus_suite_backend.dashboard.dtos.DashboardResponseDto;
import com.mx.asc.sanus_suite_backend.dashboard.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/dashboard/api/v1")
public class DashboardController {

  @Autowired
  private DashboardService dashboardService;

  @GetMapping("/resumen")
  public ResponseEntity<Map<String, Object>> obtenerResumen(@RequestHeader("x-tenant-id") String tenantId) {
    DashboardResponseDto resumen = dashboardService.obtenerResumen(tenantId);
    Map<String, Object> response = new HashMap<>();
    response.put("mensaje", "Operación Exitosa");
    response.put("folio", UUID.randomUUID().toString().substring(0, 8));
    response.put("resultado", resumen);
    return ResponseEntity.ok(response);
  }
}