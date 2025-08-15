package com.banco.mscuentas.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.*;

@Configuration
public class AmqpConfig {
  public static final String EXCHANGE = "bank.events";
  @Bean public TopicExchange bankExchange(){ return new TopicExchange(EXCHANGE, true, false); }
  
  @Bean
  public MessageConverter jsonMessageConverter() {
      return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory cf, MessageConverter jsonMessageConverter) {
      var tpl = new RabbitTemplate(cf);
      tpl.setMessageConverter(jsonMessageConverter);
      return tpl;
  }  
}


