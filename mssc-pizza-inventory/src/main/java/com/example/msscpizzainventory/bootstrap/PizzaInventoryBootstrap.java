package com.example.msscpizzainventory.bootstrap;

import com.example.msscpizzainventory.domain.PizzaInventory;
import com.example.msscpizzainventory.repository.PizzaInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PizzaInventoryBootstrap implements CommandLineRunner {

    private final PizzaInventoryRepository pizzaInventoryRepository;

    public static final String PIZZA_1_UPC = "0631234200036";
    public static final String PIZZA_2_UPC = "0631234300019";
    public static final String PIZZA_3_UPC = "0083783375213";
    private static final Long PIZZA_1_ID = 1L;
    private static final Long PIZZA_2_ID = 345555L;
    private static final Long PIZZA_3_ID = 585954L;

    @Override
    public void run(String... args) throws Exception {
        if (pizzaInventoryRepository.count() == 0) {
            loadInitialInv();
        }
    }

    private void loadInitialInv() {
        PizzaInventory pizzaInventory1 = PizzaInventory.builder()
                .pizzaId(PIZZA_1_ID)
                .upc(PIZZA_1_UPC)
                .quantityOnHand(50)
                .build();

        PizzaInventory pizzaInventory2 = PizzaInventory.builder()
                .pizzaId(PIZZA_2_ID)
                .upc(PIZZA_2_UPC)
                .quantityOnHand(50)
                .build();

        PizzaInventory pizzaInventory3 = PizzaInventory.builder()
                .pizzaId(PIZZA_3_ID)
                .upc(PIZZA_3_UPC)
                .quantityOnHand(50)
                .build();

        pizzaInventoryRepository.saveAndFlush(pizzaInventory1);
        pizzaInventoryRepository.saveAndFlush(pizzaInventory2);
        pizzaInventoryRepository.saveAndFlush(pizzaInventory3);

        log.debug("Loaded Inventory. Record count: " + pizzaInventoryRepository.count());
    }
}
