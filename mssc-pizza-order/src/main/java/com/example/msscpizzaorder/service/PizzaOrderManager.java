package com.example.msscpizzaorder.service;

import com.example.model.dto.PizzaOrderDto;
import com.example.msscpizzaorder.domain.PizzaOrder;

public interface PizzaOrderManager {

    PizzaOrder newPizzaOrder(PizzaOrder pizzaOrder);

    void processValidationResult(Long pizzaOrderId, Boolean isValid);

    void pizzaOrderAllocationPassed(PizzaOrderDto pizzaOrderDto);

    void pizzaOrderAllocationPendingInventory(PizzaOrderDto pizzaOrderDto);

    void pizzaOrderAllocationFailed(PizzaOrderDto pizzaOrderDto);

    void pizzaOrderPickedUp(Long id);

    void cancelOrder(Long id);


}
