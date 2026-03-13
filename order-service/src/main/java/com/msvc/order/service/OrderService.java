package com.msvc.order.service;

import com.msvc.order.dto.InventarioResponse;
import com.msvc.order.dto.OrderLineItemDto;
import com.msvc.order.dto.OrderRequest;
import com.msvc.order.entity.Order;
import com.msvc.order.entity.OrderLineItems;
import com.msvc.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service // Indica que esta clase es un bean de servicio de Spring
@Transactional // Asegura que todos los métodos mantengan coherencia transaccional con la BD
public class OrderService {

    private final OrderRepository orderRepository;

    // private final WebClient webClient;
    private final WebClient.Builder webClientBuilder;

    // Constructor para inyeccion de dependencias
    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }

    // Procesa y guarda un nuevo pedido
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        // Asignar un identificador unico
        order.setNumeroPedido(UUID.randomUUID().toString());
        // Transformar lista de DTOs a entidades usando Stream API
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemDtoList()
                .stream() // Convierte la lista en un flujo para procesamiento funcional
                .map(this::mapToDto) // Aplica la conversión a cada elemento
                .collect(Collectors.toList()); // Recolecta los resultados en una nueva lista
        // Establecer la relación bidireccional
        order.setOrderLineItems(orderLineItems);

        // Obtener solo los codigos SKU de cada producto en una lista ["iphone_15", "samsung_s23", "xiaomi_13"]
        List<String> codigoSku = order.getOrderLineItems().stream()
                .map(OrderLineItems::getCodigoSku)
                .collect(Collectors.toList());

        System.out.println("Codigos sku : " + codigoSku);

        // Llamar a inventario-service para verificar disponibilidad
        // Envía la lista de SKUs como parámetros de consulta
        InventarioResponse[] inventarioResponseArray = webClientBuilder.build().get()
                // .uri("http://localhost:8082/api/inventario", uriBuilder -> uriBuilder
                .uri("http://inventario-service/api/inventario", uriBuilder -> uriBuilder
                        .queryParam("codigoSku", codigoSku).build())
                .retrieve()
                // .bodyToMono(InventarioResponse[].class)
                .bodyToMono(InventarioResponse[].class)
                .block();

        // Verificar si TODOS los productos tienen stock disponible
        // allMatch() retorna true si TODOS los elementos cumplen la condición
        boolean allProductosInStock = Arrays.stream(inventarioResponseArray)
                .allMatch(InventarioResponse::isInStock);

        // Si todos los productos estan disponibles, entonces guardamos
        if (allProductosInStock) {
            // Persistir en base de datos
            orderRepository.save(order);
        } else {
            // Lanzar excepción si algún producto no tiene stock
            throw new IllegalArgumentException("El producto no esta en stock");
        }
    }

    // Convierte un DTO (OrderLineItemDto) a una entidad (OrderLineItems).
    private OrderLineItems mapToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrecio(orderLineItemDto.getPrecio());
        orderLineItems.setCantidad(orderLineItemDto.getCantidad());
        orderLineItems.setCodigoSku(orderLineItemDto.getCodigoSku());
        return orderLineItems;
    }
}
