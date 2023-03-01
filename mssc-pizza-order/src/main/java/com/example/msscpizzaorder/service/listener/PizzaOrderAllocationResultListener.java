package com.example.msscpizzaorder.service.listener;

import com.example.model.events.AllocateOrderResult;
import com.example.msscpizzaorder.config.JmsConfig;
import com.example.msscpizzaorder.service.PizzaOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PizzaOrderAllocationResultListener {

    private final PizzaOrderManager pizzaOrderManager;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_RESPONSE)
    public void listen(AllocateOrderResult result) {
        if (!result.getAllocationError() && !result.getPendingInventory()) {
            pizzaOrderManager.pizzaOrderAllocationPassed(result.getPizzaOrderDto());
        } else if (!result.getAllocationError() && result.getPendingInventory()) {
            pizzaOrderManager.pizzaOrderAllocationPendingInventory(result.getPizzaOrderDto());
        } else if (result.getAllocationError()) {
            pizzaOrderManager.pizzaOrderAllocationFailed(result.getPizzaOrderDto());
        }
    }
}
