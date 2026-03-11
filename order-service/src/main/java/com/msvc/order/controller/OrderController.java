package com.msvc.order.controller;

import com.msvc.order.dto.OrderRequest;
import com.msvc.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> realizarPedido(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return Map.of("mensaje", "Pedido realizado con éxito");
    }
}
