package com.example.msscpizzaservice.service.cooking;

import com.example.model.evetns.PizzaEvent;
import com.example.msscpizzaservice.domain.Pizza;
import com.example.msscpizzaservice.mapper.PizzaMapper;
import com.example.msscpizzaservice.repository.PizzaRepository;
import com.example.msscpizzaservice.service.inventory.PizzaInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.msscpizzaservice.config.JmsConfig.COOKING_REQUEST_QUEUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class CookingService {

    private final PizzaRepository pizzaRepository;
    private final PizzaInventoryService pizzaInventoryService;
    private final PizzaMapper pizzaMapper;
    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedDelay = 10000L)
    public void checkForLowInventory() {
        List<Pizza> pizzas = pizzaRepository.findAll();

        pizzas.forEach(pizza -> {
            Integer inventoryQOH = pizzaInventoryService.getOnhandInventory(pizza.getId());
            log.debug("Min OnHand is: " + pizza.getQuantityOnHand());
            log.debug("Inventory is: " + inventoryQOH);

            if (pizza.getQuantityOnHand() >= inventoryQOH) {
                jmsTemplate.convertAndSend(COOKING_REQUEST_QUEUE, new PizzaEvent(pizzaMapper.pizzaToPizzaDto(pizza)));
            }
        });
    }
}
