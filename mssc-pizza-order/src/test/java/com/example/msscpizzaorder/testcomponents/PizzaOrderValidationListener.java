package com.example.msscpizzaorder.testcomponents;

import com.example.model.events.ValidateOrderRequest;
import com.example.model.events.ValidateOrderResult;
import com.example.msscpizzaorder.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.example.msscpizzaorder.config.JmsConfig.VALIDATE_ORDER_QUEUE;

@Component
@RequiredArgsConstructor
public class PizzaOrderValidationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = VALIDATE_ORDER_QUEUE)
    public void list(Message msg){

        ValidateOrderRequest request = (ValidateOrderRequest) msg.getPayload();

        System.out.println("################ I RAN #################");

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                ValidateOrderResult.builder()
                        .isValid(true)
                        .orderId(request.getPizzaOrderDto().getId())
                        .build());

    }
}
