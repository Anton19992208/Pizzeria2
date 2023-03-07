package com.example.model.event;

import com.example.model.dto.PizzaDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends PizzaEvent {

    public NewInventoryEvent(PizzaDto pizzaDto) {
        super(pizzaDto);
    }
}
