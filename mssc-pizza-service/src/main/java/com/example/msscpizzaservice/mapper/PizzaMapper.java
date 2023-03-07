package com.example.msscpizzaservice.mapper;

import com.example.model.dto.PizzaDto;
import com.example.msscpizzaservice.domain.Pizza;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(PizzaMapperDecorator.class)
public interface PizzaMapper {

    PizzaDto pizzaToPizzaDto(Pizza pizza);

    PizzaDto pizzaToPizzaDtoWithInventory(Pizza pizza);

    Pizza pizzaDtoToPizza(PizzaDto pizzaDto);


}
