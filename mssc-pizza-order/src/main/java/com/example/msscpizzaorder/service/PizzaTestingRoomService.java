package com.example.msscpizzaorder.service;

import com.example.model.dto.PizzaOrderDto;
import com.example.model.dto.PizzaOrderLineDto;
import com.example.msscpizzaorder.bootstrap.PizzaOrderBootStrap;
import com.example.msscpizzaorder.domain.Customer;
import com.example.msscpizzaorder.repository.CustomerRepository;
import com.example.msscpizzaorder.repository.PizzaOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.msscpizzaorder.bootstrap.PizzaOrderBootStrap.PIZZA_1_UPC;
import static com.example.msscpizzaorder.bootstrap.PizzaOrderBootStrap.PIZZA_2_UPC;
import static com.example.msscpizzaorder.bootstrap.PizzaOrderBootStrap.PIZZA_3_UPC;
import static com.example.msscpizzaorder.bootstrap.PizzaOrderBootStrap.TASTING_ROOM;

@Slf4j
@Component
public class PizzaTestingRoomService {

    private final PizzaOrderService pizzaOrderService;
    private final PizzaOrderRepository pizzaOrderRepository;
    private final CustomerRepository customerRepository;
    private final List<String> pizzaUpcs = new ArrayList<>(3);

    public PizzaTestingRoomService(PizzaOrderService pizzaOrderService,
                                   PizzaOrderRepository pizzaOrderRepository,
                                   CustomerRepository customerRepository) {
        this.pizzaOrderService = pizzaOrderService;
        this.pizzaOrderRepository = pizzaOrderRepository;
        this.customerRepository = customerRepository;

        pizzaUpcs.add(PIZZA_1_UPC);
        pizzaUpcs.add(PIZZA_2_UPC);
        pizzaUpcs.add(PIZZA_3_UPC);
    }

    @Transactional
    @Scheduled(fixedRate = 2000)
    public void placeTestingRoomOrder() {
        List<Customer> customers = customerRepository.findAllByCustomerNameLike(TASTING_ROOM);

        if (customers.size() == 1) {
            doPlaceOrder(customers.get(0));
        } else {
            log.error("Too many or too few tasting room customers found");
            customers.forEach(customer -> log.debug(customer.toString()));
        }
    }

    private void doPlaceOrder(Customer customer) {
        String pizzaToOrder = getRandomUpc();

        PizzaOrderLineDto pizzaOrderLineDto = PizzaOrderLineDto.builder()
                .upc(pizzaToOrder)
                .orderQuantity(new Random().nextInt(6))
                .build();

        List<PizzaOrderLineDto> pizzaOrderLineDtos = new ArrayList<>();
        pizzaOrderLineDtos.add(pizzaOrderLineDto);

        PizzaOrderDto pizzaOrderDto = PizzaOrderDto.builder()
                .customerId(customer.getId())
                .customerRef(new Random().toString())
                .pizzaOrderLines(pizzaOrderLineDtos)
                .build();

        pizzaOrderService.placeOrder(customer.getId(), pizzaOrderDto);

    }

    private String getRandomUpc() {
        return pizzaUpcs.get(new Random().nextInt(pizzaUpcs.size()));
    }
}
