package com.example.model.evetns;

import com.example.model.dto.PizzaDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends PizzaEvent {

    public NewInventoryEvent(PizzaDto pizzaDto) {
        super(pizzaDto);
    }
}
