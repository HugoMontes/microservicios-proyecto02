package com.msvc.inventario.service;

import com.msvc.inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Transactional(readOnly = true)
    public boolean isInStock(String codigoSku) {
        return inventarioRepository.findByCodigoSku(codigoSku).isPresent();
    }
}
