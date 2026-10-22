package com.mx.asc.sanus_suite_backend.inventario.controllers;

import com.mx.asc.sanus_suite_backend.inventario.dtos.ProductoRequestDto;
import com.mx.asc.sanus_suite_backend.inventario.entities.Producto;
import com.mx.asc.sanus_suite_backend.inventario.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/inventario/api/v1")
public class ProductoController {

  @Autowired
  private ProductoService productoService;

  @GetMapping("/listar")
  public ResponseEntity<Map<String, Object>> listar(@RequestHeader("x-tenant-id") String tenantId) {
    List<Producto> resultado = productoService.listarProductos(tenantId);
    Map<String, Object> response = new HashMap<>();
    response.put("mensaje", "Operación Exitosa");
    response.put("folio", UUID.randomUUID().toString().substring(0, 8));
    response.put("resultado", resultado);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/crear")
  public ResponseEntity<Map<String, Object>> crear(
    @RequestHeader("x-tenant-id") String tenantId,
    @RequestBody ProductoRequestDto dto) {
    Producto productoCreado = productoService.crearProducto(tenantId, dto);
    Map<String, Object> response = new HashMap<>();
    response.put("mensaje", "Producto registrado con éxito");
    response.put("folio", UUID.randomUUID().toString().substring(0, 8));
    response.put("resultado", productoCreado);
    return ResponseEntity.ok(response);
  }
}