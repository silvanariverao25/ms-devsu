package com.banco.mscuentas.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.*;

@Configuration
public class RabbitTopologyConfig {
  public static final String EXCHANGE = "bank.events";
  public static final String Q_CLIENTE_CREATED = "cliente.created.q";
  public static final String Q_CLIENTE_UPDATED = "cliente.updated.q";
  public static final String Q_CLIENTE_DELETED = "cliente.deleted.q";
  public static final String RK_CLIENTE_CREATED = "cliente.created";
  public static final String RK_CLIENTE_UPDATED = "cliente.updated";
  public static final String RK_CLIENTE_DELETED = "cliente.deleted";

  @Bean TopicExchange bankEventsExchange(){ return new TopicExchange(EXCHANGE, true, false); }
  @Bean Queue clienteCreatedQueue(){ return QueueBuilder.durable(Q_CLIENTE_CREATED).build(); }
  @Bean Queue clienteUpdatedQueue(){ return QueueBuilder.durable(Q_CLIENTE_UPDATED).build(); }
  @Bean Queue clienteDeletedQueue(){ return QueueBuilder.durable(Q_CLIENTE_DELETED).build(); }
  @Bean Binding bindClienteCreated(Queue clienteCreatedQueue, TopicExchange bankEventsExchange){
    return BindingBuilder.bind(clienteCreatedQueue).to(bankEventsExchange).with(RK_CLIENTE_CREATED);
  }
  @Bean Binding bindClienteUpdated(Queue clienteUpdatedQueue, TopicExchange bankEventsExchange){
    return BindingBuilder.bind(clienteUpdatedQueue).to(bankEventsExchange).with(RK_CLIENTE_UPDATED);
  }
  @Bean Binding bindClienteDeleted(Queue clienteDeletedQueue, TopicExchange bankEventsExchange){
    return BindingBuilder.bind(clienteDeletedQueue).to(bankEventsExchange).with(RK_CLIENTE_DELETED);
  }
}
