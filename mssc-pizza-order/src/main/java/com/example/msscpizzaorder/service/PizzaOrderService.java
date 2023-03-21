package com.example.msscpizzaorder.service;

import com.example.model.dto.PizzaOrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PizzaOrderService {

    Page<PizzaOrderDto> listOrders(Long customerId, Pageable pageable);

    PizzaOrderDto placeOrder(Long customerId, PizzaOrderDto pizzaOrderDto);

    PizzaOrderDto getOrderById(Long customerId, Long orderId);

    void pickupOrder(Long orderId);
}

