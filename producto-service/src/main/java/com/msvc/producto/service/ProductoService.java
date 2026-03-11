package com.msvc.producto.service;

import com.msvc.producto.dto.ProductoRequest;
import com.msvc.producto.dto.ProductoResponse;
import com.msvc.producto.model.Producto;
import com.msvc.producto.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public void createProducto(ProductoRequest productoRequest) {
        // Convertir el DTO de entrada a una entidad Producto (usando Builder de Lombok)
        Producto producto = Producto.builder()
                .nombre(productoRequest.getNombre())
                .descripcion(productoRequest.getDescripcion())
                .precio(productoRequest.getPrecio())
                .build();
        // Guardar en la base de datos MongoDB
        productoRepository.save(producto);
        // Log informativo con el ID generado automáticamente por MongoDB
        log.info("Producto {} fue guardado con exito", producto.getId());
    }

    public List<ProductoResponse> getAllProductos() {
        // Obtener todas las entidades Producto desde MongoDB
        List<Producto> productos = productoRepository.findAll();
        // Transformar la lista de entidades a lista de DTOs usando Stream API
        return productos.stream()                    // Convierte la lista en un flujo de datos
                .map(this::mapToProductoResponse)    // Transforma cada Producto en ProductoResponse
                .collect(Collectors.toList());       // Recolecta los resultados en una nueva lista
    }

    // Metodo para convertir una entidad Producto en un DTO ProductoResponse.
    private ProductoResponse mapToProductoResponse(Producto producto) {
        return ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .build();
    }
}
