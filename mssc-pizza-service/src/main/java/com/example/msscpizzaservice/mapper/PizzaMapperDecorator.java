package com.example.msscpizzaservice.mapper;

import com.example.model.dto.PizzaDto;
import com.example.msscpizzaservice.domain.Pizza;
import com.example.msscpizzaservice.service.inventory.PizzaInventoryService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class PizzaMapperDecorator implements PizzaMapper{

    private PizzaInventoryService pizzaInventoryService;
    private PizzaMapper pizzaMapper;

    @Autowired
    public void setPizzaInventoryService(PizzaInventoryService pizzaInventoryService) {
        this.pizzaInventoryService = pizzaInventoryService;
    }

    @Autowired
    public void setPizzaMapper(PizzaMapper pizzaMapper) {
        this.pizzaMapper = pizzaMapper;
    }

    @Override
    public PizzaDto pizzaToPizzaDto(Pizza pizza) {
        return pizzaMapper.pizzaToPizzaDto(pizza);
    }

    @Override
    public PizzaDto pizzaToPizzaDtoWithInventory(Pizza pizza) {
         PizzaDto pizzaDto = pizzaMapper.pizzaToPizzaDto(pizza);
         pizzaDto.setQuantityOnHand(pizzaInventoryService.getOnhandInventory(pizza.getId()));
         return pizzaDto;
    }

    @Override
    public Pizza pizzaDtoToPizza(PizzaDto pizzaDto) {
        return pizzaMapper.pizzaDtoToPizza(pizzaDto);
    }
}
