package com.example.msscpizzainventory.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class JmsConfig {

    public static final String NEW_INVENTORY_QUEUE = "new-inventory";
    public static final String ALLOCATE_ORDER_QUEUE = "allocate-order";
    public static final String ALLOCATE_ORDER_RESPONSE = "allocate-order-response";
    public static final String DEALLOCATE_ORDER_QUEUE = "deallocate-order";

    @Bean
    public MappingJackson2MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();

        jmsListenerContainerFactory.setConnectionFactory(connectionFactory);
        jmsListenerContainerFactory.setConcurrency("2-4");
        jmsListenerContainerFactory.setMessageConverter(jacksonJmsMessageConverter(objectMapper()));

        return jmsListenerContainerFactory;
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
