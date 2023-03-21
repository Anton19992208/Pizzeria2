package com.example.msscpizzaorder.mapper;

import com.example.model.dto.PizzaDto;
import com.example.model.dto.PizzaOrderLineDto;
import com.example.msscpizzaorder.domain.PizzaOrderLine;
import com.example.msscpizzaorder.service.pizza.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


public abstract class PizzaOrderLineMapperDecorator implements PizzaOrderLineMapper {

    private PizzaService pizzaService;
    private PizzaOrderLineMapper pizzaOrderLineMapper;

    @Autowired
    public void setPizzaService(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @Autowired
    public void setPizzaOrderLineMapper(PizzaOrderLineMapper pizzaOrderLineMapper) {
        this.pizzaOrderLineMapper = pizzaOrderLineMapper;
    }

    @Override
    public PizzaOrderLineDto pizzaOrderLineToPizzaOrderLineDto(PizzaOrderLine pizzaOrderLine) {
        PizzaOrderLineDto pizzaOrderLineDto = pizzaOrderLineMapper.pizzaOrderLineToPizzaOrderLineDto(pizzaOrderLine);
        Optional<PizzaDto> optionalPizzaDto = pizzaService.findPizzaByUpc(pizzaOrderLine.getUpc());

        optionalPizzaDto.ifPresent(pizzaDto -> {
            pizzaOrderLineDto.setPizzaId(pizzaDto.getId());
            pizzaOrderLineDto.setPizzaName(pizzaDto.getName());
            pizzaOrderLineDto.setPrice(pizzaDto.getPrice());
            pizzaOrderLineDto.setPizzaSize(pizzaDto.getSize());
        });
        return pizzaOrderLineDto;
    }


}
