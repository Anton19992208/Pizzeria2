package com.example.model.events;

import com.example.model.dto.PizzaOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocateOrderResult {

    private PizzaOrderDto pizzaOrderDto;
    private Boolean allocationError = false;
    private Boolean pendingInventory = false;
}
