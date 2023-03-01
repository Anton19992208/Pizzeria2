package com.example.msscpizzainventory.service;

import com.example.model.dto.PizzaOrderDto;

public interface AllocationService {

    Boolean allocateOrder(PizzaOrderDto pizzaOrderDto);

    void deallocateOrder(PizzaOrderDto pizzaOrderDto);
}
