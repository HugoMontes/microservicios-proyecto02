package com.msvc.order.controller;

import com.msvc.order.dto.OrderRequest;
import com.msvc.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventarioService", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventarioService")
    @Retry(name = "inventarioService")
    public CompletableFuture<String> realizarPedido(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    public CompletableFuture<Map<String, String>> fallBackMethod(OrderRequest orderRequest, Throwable  throwable) {
        return CompletableFuture.supplyAsync(() ->
                Map.of("mensaje", "Oops! Ha ocurrido un error al realizar el pedido")
        );
    }
}
