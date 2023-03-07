package com.example.msscpizzaorder.web.controller;

import com.example.model.dto.PizzaOrderDto;
import com.example.model.dto.PizzaOrderPagedList;
import com.example.msscpizzaorder.service.PizzaOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/v1/customers/{customerId}/")
@RequiredArgsConstructor
public class PizzaOrderController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final PizzaOrderService pizzaOrderService;

    @GetMapping("orders")
    public PizzaOrderPagedList findAll(@PathVariable("customerId") Long customerId,
                                       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return pizzaOrderService.listOrders(customerId, PageRequest.of(pageNumber, pageSize));
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
