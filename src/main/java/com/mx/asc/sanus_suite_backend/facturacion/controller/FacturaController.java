package com.mx.asc.sanus_suite_backend.facturacion.controller;

import com.mx.asc.sanus_suite_backend.facturacion.dtos.FacturaRequestDto;
import com.mx.asc.sanus_suite_backend.facturacion.entities.Factura;
import com.mx.asc.sanus_suite_backend.facturacion.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/facturas/api/v1")
public class FacturaController {

  @Autowired
  private FacturaService facturaService;

  @GetMapping("/listar")
  public ResponseEntity<Map<String, Object>> listar(@RequestHeader("x-tenant-id") String tenantId) {
    List<Factura> resultado = facturaService.listarFacturas(tenantId);
    Map<String, Object> response = new HashMap<>();
    response.put("mensaje", "Operación Exitosa");
    response.put("folio", UUID.randomUUID().toString().substring(0, 8));
    response.put("resultado", resultado);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/crear")
  public ResponseEntity<Map<String, Object>> crear(
    @RequestHeader("x-tenant-id") String tenantId,
    @RequestBody FacturaRequestDto dto) {
    Factura facturaCreada = facturaService.crearFactura(tenantId, dto);
    Map<String, Object> response = new HashMap<>();
    response.put("mensaje", "Nota de venta creada con éxito");
    response.put("folio", UUID.randomUUID().toString().substring(0, 8));
    response.put("resultado", facturaCreada);
    return ResponseEntity.ok(response);
  }
}
