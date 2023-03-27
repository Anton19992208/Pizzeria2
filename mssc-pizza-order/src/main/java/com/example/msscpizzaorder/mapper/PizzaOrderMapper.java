package com.example.msscpizzaorder.mapper;

import com.example.model.dto.PizzaOrderDto;
import com.example.msscpizzaorder.domain.PizzaOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = PizzaOrderLineMapper.class)
public interface PizzaOrderMapper {

    @Mapping(target = "customerId", source = "customer.id")
    PizzaOrderDto pizzaOrderToPizzaOrderDto(PizzaOrder pizzaOrder);

    PizzaOrder pizzaOrderDtoToPizzaOrder(PizzaOrderDto pizzaOrderDto);
}
