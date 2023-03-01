package com.example.msscpizzaorder.service;

import com.example.model.dto.PizzaOrderDto;
import com.example.model.dto.PizzaOrderPagedList;
import org.springframework.data.domain.Pageable;

public interface PizzaOrderService {

    PizzaOrderPagedList listOrders(Long customerId, Pageable pageable);

    PizzaOrderDto placeOrder(Long customerId, PizzaOrderDto pizzaOrderDto);

    PizzaOrderDto getOrderById(Long customerId, Long orderId);

    void pickupOrder(Long orderId);
}

