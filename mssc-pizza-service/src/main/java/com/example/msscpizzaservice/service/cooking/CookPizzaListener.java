package com.example.msscpizzaservice.service.cooking;

import com.example.model.dto.PizzaDto;
import com.example.model.evetns.CookPizzaEvent;
import com.example.model.evetns.NewInventoryEvent;
import com.example.msscpizzaservice.config.JmsConfig;
import com.example.msscpizzaservice.domain.Pizza;
import com.example.msscpizzaservice.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.msscpizzaservice.config.JmsConfig.COOKING_REQUEST_QUEUE;
import static com.example.msscpizzaservice.config.JmsConfig.NEW_INVENTORY_QUEUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class CookPizzaListener {

    private final PizzaRepository pizzaRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = COOKING_REQUEST_QUEUE)
    public void listen(CookPizzaEvent cookPizzaEvent) {
        PizzaDto pizzaDto = cookPizzaEvent.getPizzaDto();

        Pizza pizza = pizzaRepository.findById(pizzaDto.getId()).get();

        pizzaDto.setQuantityOnHand(pizza.getQuantityOnHand());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(pizzaDto);

        log.debug("Cooked pizza " + pizza.getQuantityOnHand() + ", QOH: " + pizzaDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
