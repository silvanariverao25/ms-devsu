package com.banco.msclientes.infrastructure.messaging;

import com.banco.msclientes.domain.port.ClienteEventsPublisherPort;
import com.banco.msclientes.infrastructure.config.AmqpConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RabbitClienteEventsPublisher implements ClienteEventsPublisherPort {

    private final RabbitTemplate template;

    public RabbitClienteEventsPublisher(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void publishCreated(UUID clienteId, String identificacion, short estado) {
        template.convertAndSend(AmqpConfig.EXCHANGE, "cliente.created",
                new ClienteEvent(clienteId, identificacion, estado));
    }

    @Override
    public void publishUpdated(UUID clienteId, String identificacion, short estado) {
        template.convertAndSend(AmqpConfig.EXCHANGE, "cliente.updated",
                new ClienteEvent(clienteId, identificacion, estado));
    }

    @Override
    public void publishDeleted(UUID clienteId) {
        template.convertAndSend(AmqpConfig.EXCHANGE, "cliente.deleted",
                new ClienteDeletedEvent(clienteId));
    }

    // Representaciones simples del evento
    public record ClienteEvent(UUID clienteId, String identificacion, short estado) {}
    public record ClienteDeletedEvent(UUID clienteId) {}
}
