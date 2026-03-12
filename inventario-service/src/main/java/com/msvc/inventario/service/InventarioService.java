package com.msvc.inventario.service;

import com.msvc.inventario.dto.InventarioResponse;
import com.msvc.inventario.entity.Inventario;
import com.msvc.inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Transactional(readOnly = true)
    public List<InventarioResponse> isInStock(List<String> listaCodigosSku) {
        // Buscar en BD todos los productos cuyos SKU estén en la lista proporcionada
        List<Inventario> inventarioEncontrado = inventarioRepository.findByCodigoSkuIn(listaCodigosSku);
        // Transformar la lista de entidades a DTOs usando Stream API
        return inventarioEncontrado.stream()
                // Para cada entidad Inventario, crear un objeto InventarioResponse
                .map(inventario -> InventarioResponse.builder()
                        .codigoSku(inventario.getCodigoSku())  // Copiar el SKU
                        .inStock(inventario.getCantidad() > 0) // Calcular disponibilidad
                        .build())
                .collect(Collectors.toList()); // Recolectar todos los resultados en una nueva lista
        // Si un SKU no existe en la BD, simplemente no aparecerá en la respuesta.
    }
}
