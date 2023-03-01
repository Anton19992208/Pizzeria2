package com.example.msscpizzaorder.service.listener;

import com.example.model.events.ValidateOrderResult;
import com.example.msscpizzaorder.service.PizzaOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.example.msscpizzaorder.config.JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE;

@Slf4j
@RequiredArgsConstructor
@Component
public class ValidationResultListener {

    private final PizzaOrderManager pizzaOrderManager;

    @JmsListener(destination = VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listen(ValidateOrderResult result){

        final Long pizzaOrderId = result.getOrderId();

        log.debug("Validation Result for Order Id: " + pizzaOrderId);

        pizzaOrderManager.processValidationResult(pizzaOrderId, result.getIsValid());
    }
}
