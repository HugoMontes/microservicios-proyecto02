package com.msvc.order.config.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate; // Proporciona métodos para enviar y recibir mensajes

    @Value("${rabbitmq.exchange}")
    private String exchange;               // Nombre del exchange donde se publicarán los mensajes.

    @Value("${rabbitmq.routing-key}")
    private String routingKey;             // Clave de enrutamiento (routing key) para los mensajes.

    // Envía un mensaje al exchange configurado con la routing key.
    public void send(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
