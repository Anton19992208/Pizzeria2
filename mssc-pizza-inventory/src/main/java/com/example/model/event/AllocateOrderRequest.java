package com.example.model.event;

import com.example.model.dto.PizzaOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocateOrderRequest {

    private PizzaOrderDto pizzaOrderDto;
}
