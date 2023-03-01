package com.example.msscpizzaservice.service.order;

import com.example.model.dto.PizzaOrderDto;
import com.example.msscpizzaservice.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class PizzaOrderValidator {

    private final PizzaRepository pizzaRepository;

    public Boolean validateOrder(PizzaOrderDto pizzaOrderDto) {
        AtomicInteger pizzaNotFound = new AtomicInteger();

        pizzaOrderDto.getPizzaOrderLines().forEach(pizzaOrderLineDto -> {
            if (pizzaRepository.findByUpc(pizzaOrderLineDto.getUpc()) == null)
                pizzaNotFound.incrementAndGet();
        });
        return pizzaNotFound.get() == 0;
    }
}
