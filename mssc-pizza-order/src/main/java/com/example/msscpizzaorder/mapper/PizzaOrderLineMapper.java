package com.example.msscpizzaorder.mapper;

import com.example.model.dto.PizzaOrderLineDto;
import com.example.msscpizzaorder.domain.PizzaOrderLine;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(PizzaOrderLineMapperDecorator.class)
public interface PizzaOrderLineMapper {

    PizzaOrderLineDto pizzaOrderLineToPizzaOrderLineDto(PizzaOrderLine pizzaOrderLine);

    PizzaOrderLine pizzaOrderLineDtoToPizzaOrderLine(PizzaOrderLineDto pizzaOrderLineDto);
}
