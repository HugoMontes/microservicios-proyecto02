package com.msvc.inventario.controller;

import com.msvc.inventario.service.InventarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/{codigoSku}")
    public boolean isInStock(@PathVariable("codigoSku") String codigoSku) {
        return inventarioService.isInStock(codigoSku);
    }
}
