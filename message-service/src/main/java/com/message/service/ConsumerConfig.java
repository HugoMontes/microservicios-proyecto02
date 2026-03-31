package com.message.service;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {

    @Value("${rabbitmq.queue.name}")
    private String queueName;       // Nombre de la cola exclusiva de este servicio.

    @Value("${rabbitmq.exchange}")
    private String exchangeName;    // Nombre del exchange donde se publican los eventos.

    @Value("${rabbitmq.routing-key}")
    private String routingKey;      // Clave de enrutamiento para filtrar mensajes.

    // Declara la COLA donde se almacenarán los mensajes para este servicio.
    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }
    // Declara el EXCHANGE directo donde se publican los mensajes.
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }
    // Declara el BINDING (vinculación) entre la cola y el exchange.
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)        // ¿Qué cola?
                .to(exchange)       // ¿A qué exchange?
                .with(routingKey);  // ¿Con qué routing key?
    }
}
