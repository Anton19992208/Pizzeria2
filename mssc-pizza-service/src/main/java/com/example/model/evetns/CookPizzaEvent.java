package com.example.model.evetns;

import com.example.model.dto.PizzaDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CookPizzaEvent extends PizzaEvent {

    public CookPizzaEvent(PizzaDto pizzaDto) {
        super(pizzaDto);
    }
}
