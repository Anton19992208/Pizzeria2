package com.example.msscpizzainventory.listener;

import com.example.model.event.NewInventoryEvent;
import com.example.msscpizzainventory.config.JmsConfig;
import com.example.msscpizzainventory.domain.PizzaInventory;
import com.example.msscpizzainventory.repository.PizzaInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewInventoryListener {

    private final PizzaInventoryRepository pizzaInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent event){

        log.debug("Got Inventory: " + event.toString());

        pizzaInventoryRepository.save(PizzaInventory.builder()
                        .pizzaId(event.getPizzaDto().getId())
                        .upc(event.getPizzaDto().getUpc())
                        .quantityOnHand(event.getPizzaDto().getQuantityOnHand())
                .build());
    }
}
