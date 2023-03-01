package com.example.msscpizzaservice.service.order;

import com.example.model.evetns.ValidateOrderRequest;
import com.example.model.evetns.ValidateOrderResult;
import com.example.msscpizzaservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PizzaOrderValidationListener {

    private final PizzaOrderValidator pizzaOrderValidator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateOrderRequest validateOrderRequest) {
        Boolean isValid = pizzaOrderValidator.validateOrder(validateOrderRequest.getPizzaOrderDto());

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                ValidateOrderResult.builder()
                        .orderId(validateOrderRequest.getPizzaOrderDto().getId())
                        .isValid(isValid)
                        .build());
    }
}
