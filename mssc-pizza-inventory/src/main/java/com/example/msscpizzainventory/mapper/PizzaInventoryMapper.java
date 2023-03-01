package com.example.msscpizzainventory.mapper;

import com.example.model.dto.PizzaInventoryDto;
import com.example.msscpizzainventory.domain.PizzaInventory;
import org.mapstruct.Mapper;

@Mapper
public interface PizzaInventoryMapper {

    PizzaInventory pizzaInventoryDtoToPizzaInventory(PizzaInventoryDto pizzaInventoryDto);

    PizzaInventoryDto pizzaInventoryToPizzaInventoryDto(PizzaInventory pizzaInventory);
}
