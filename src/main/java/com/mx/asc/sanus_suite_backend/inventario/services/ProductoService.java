package com.mx.asc.sanus_suite_backend.inventario.services;

import com.mx.asc.sanus_suite_backend.inventario.dtos.ProductoRequestDto;
import com.mx.asc.sanus_suite_backend.inventario.entities.Producto;
import com.mx.asc.sanus_suite_backend.inventario.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

  @Autowired
  private ProductoRepository productoRepository;

  public List<Producto> listarProductos(String tenantId) {
    return productoRepository.findByTenantId(tenantId);
  }

  public Producto crearProducto(String tenantId, ProductoRequestDto dto) {
    Producto producto = new Producto();
    producto.setTenantId(tenantId);
    producto.setNombre(dto.getNombre());
    producto.setCodigo(dto.getCodigo());
    producto.setDescripcion(dto.getDescripcion());
    producto.setPrecioCompra(dto.getPrecioCompra());
    producto.setPrecioVenta(dto.getPrecioVenta());
    producto.setStockActual(dto.getStockActual() != null ? dto.getStockActual() : 0);
    producto.setStockMinimo(dto.getStockMinimo() != null ? dto.getStockMinimo() : 5);
    return productoRepository.save(producto);
  }
}