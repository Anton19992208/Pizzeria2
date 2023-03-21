package com.example.msscpizzaorder.web.controller;

import com.example.model.dto.PizzaOrderDto;
import com.example.msscpizzaorder.service.PizzaOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/customers/{customerId}/")
@RequiredArgsConstructor
public class PizzaOrderController {

    private final PizzaOrderService pizzaOrderService;

    @GetMapping("orders")
    public Page<PizzaOrderDto> findAll(@PathVariable("customerId") Long customerId, Pageable pageable) {

        return pizzaOrderService.listOrders(customerId, pageable);
    }

    @PostMapping("orders")
    @ResponseStatus(CREATED)
    public PizzaOrderDto placeOrder(@PathVariable("customerId") Long customerId, @RequestBody PizzaOrderDto pizzaOrderDto) {
        return pizzaOrderService.placeOrder(customerId, pizzaOrderDto);
    }

    @GetMapping("orders/{orderId}")
    public PizzaOrderDto getOrder(@PathVariable("customerId") Long customerId, @PathVariable("orderId") Long orderId) {
        return pizzaOrderService.getOrderById(customerId, orderId);
    }

    @PutMapping("/orders/{orderId}/pickup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pickupOrder(@PathVariable("customerId") Long customerId, @PathVariable("orderId") Long orderId) {
        pizzaOrderService.pickupOrder(orderId);
    }

}
