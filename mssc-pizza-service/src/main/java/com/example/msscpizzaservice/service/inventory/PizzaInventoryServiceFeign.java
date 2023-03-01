package com.example.msscpizzaservice.service.inventory;

import com.example.model.dto.PizzaInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Profile("local-discovery")
@Service
@RequiredArgsConstructor
public class PizzaInventoryServiceFeign implements PizzaInventoryService{

   private final InventoryServiceFeignClient inventoryServiceFeignClient;

    @Override
    public Integer getOnhandInventory(Long pizzaId) {
        log.debug("Calling inventory Service - PizzaId: " + pizzaId);

        ResponseEntity<List<PizzaInventoryDto>> responseEntity = inventoryServiceFeignClient.getOnHandInventory(pizzaId);

        Integer onHnad = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(PizzaInventoryDto::getQuantityOnHand)
                .sum();

        log.debug("Pizza id: " + pizzaId + "on hand: " + onHnad);

        return onHnad;
    }
}
