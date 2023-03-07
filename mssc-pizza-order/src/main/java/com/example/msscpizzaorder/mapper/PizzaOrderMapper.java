package com.example.msscpizzaorder.mapper;

import com.example.model.dto.PizzaOrderDto;
import com.example.msscpizzaorder.domain.PizzaOrder;
import org.mapstruct.Mapper;

@Mapper(uses = PizzaOrderLineMapper.class)
public interface PizzaOrderMapper {

    PizzaOrderDto pizzaOrderToPizzaOrderDto(PizzaOrder pizzaOrder);

    PizzaOrder pizzaOrderDtoToPizzaOrder(PizzaOrderDto pizzaOrderDto);
}
