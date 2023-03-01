package com.example.msscpizzainventory.service;

import com.example.model.dto.PizzaOrderDto;
import com.example.model.dto.PizzaOrderLineDto;
import com.example.msscpizzainventory.domain.PizzaInventory;
import com.example.msscpizzainventory.repository.PizzaInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service
public class AllocationServiceImpl implements AllocationService {

    private final PizzaInventoryRepository pizzaInventoryRepository;

    @Override
    public Boolean allocateOrder(PizzaOrderDto pizzaOrderDto) {
        log.debug("Allocating OrderId: " + pizzaOrderDto.getId());

        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        pizzaOrderDto.getPizzaOrderLines().forEach(pizzaOrderLine -> {
            if ((((pizzaOrderLine.getOrderQuantity() != null ? pizzaOrderLine.getOrderQuantity() : 0)
                    - (pizzaOrderLine.getQuantityAllocated() != null ? pizzaOrderLine.getQuantityAllocated() : 0)) > 0)) {
                allocatePizzaOrderLine(pizzaOrderLine);
            }
            totalOrdered.set(totalOrdered.get() + pizzaOrderLine.getOrderQuantity());
            totalAllocated.set(totalAllocated.get() + (pizzaOrderLine.getQuantityAllocated() != null ? pizzaOrderLine.getQuantityAllocated() : 0));
        });

        log.debug("Total Ordered: " + totalOrdered.get() + " Total Allocated: " + totalAllocated.get());

        return totalOrdered.get() == totalAllocated.get();
    }

    @Override
    public void deallocateOrder(PizzaOrderDto pizzaOrderDto) {
        pizzaOrderDto.getPizzaOrderLines().forEach(pizzaOrderLineDto -> {
            PizzaInventory pizzaInventory = PizzaInventory.builder()
                    .pizzaId(pizzaOrderLineDto.getId())
                    .upc(pizzaOrderLineDto.getUpc())
                    .quantityOnHand(pizzaOrderLineDto.getQuantityAllocated())
                    .build();

            PizzaInventory savedInventory = pizzaInventoryRepository.save(pizzaInventory);

            log.debug("Saved Inventory for beer upc: " + savedInventory.getUpc() + " inventory id: " + savedInventory.getId());
        });
    }

    private void allocatePizzaOrderLine(PizzaOrderLineDto pizzaOrderLineDto) {
        List<PizzaInventory> pizzaInventoryList = pizzaInventoryRepository.findAllByUpc(pizzaOrderLineDto.getUpc());

        pizzaInventoryList.forEach(pizzaInventory -> {
            int inventory = (pizzaInventory.getQuantityOnHand() == null) ? 0 : pizzaInventory.getQuantityOnHand();
            int orderQty = (pizzaOrderLineDto.getOrderQuantity() == null) ? 0 : pizzaOrderLineDto.getOrderQuantity();
            int allocatedQty = (pizzaOrderLineDto.getQuantityAllocated() == null) ? 0 : pizzaOrderLineDto.getQuantityAllocated();
            int qtyToAllocate = orderQty - allocatedQty;


            if (inventory >= qtyToAllocate) {
                inventory = inventory - qtyToAllocate;
                pizzaOrderLineDto.setQuantityAllocated(orderQty);
                pizzaInventory.setQuantityOnHand(inventory);

                pizzaInventoryRepository.save(pizzaInventory);
            } else if (inventory > 0) {
                pizzaOrderLineDto.setQuantityAllocated(allocatedQty + inventory);
                pizzaInventory.setQuantityOnHand(0);
            }

            if (pizzaInventory.getQuantityOnHand() == 0) {
                pizzaInventoryRepository.delete(pizzaInventory);
            }
        });
    }
}
