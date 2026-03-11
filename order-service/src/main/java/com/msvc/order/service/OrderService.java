package com.msvc.order.service;

import com.msvc.order.dto.OrderLineItemDto;
import com.msvc.order.dto.OrderRequest;
import com.msvc.order.entity.Order;
import com.msvc.order.entity.OrderLineItems;
import com.msvc.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service // Indica que esta clase es un bean de servicio de Spring
@Transactional // Asegura que todos los métodos mantengan coherencia transaccional con la BD
public class OrderService {

    private final OrderRepository orderRepository;

    // Constructor para inyeccion de dependencias
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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
        // Persistir en base de datos
        orderRepository.save(order);
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
