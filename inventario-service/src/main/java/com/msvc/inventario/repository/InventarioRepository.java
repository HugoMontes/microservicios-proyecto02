package com.msvc.inventario.repository;

import com.msvc.inventario.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    // Optional<Inventario> findByCodigoSku(String codigoSku);
    List<Inventario> findByCodigoSkuIn(List<String> codigoSku);
}
