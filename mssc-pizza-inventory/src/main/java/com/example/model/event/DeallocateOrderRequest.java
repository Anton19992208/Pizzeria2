package com.example.model.event;

import com.example.model.dto.PizzaOrderDto;
import com.example.msscpizzainventory.domain.PizzaInventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeallocateOrderRequest {

    private PizzaOrderDto pizzaOrderDto;
}
