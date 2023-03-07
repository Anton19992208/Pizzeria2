package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PizzaInventoryDto {

    private Long id;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
    private Long pizzaId;
    private String upc;
    private Integer quantityOnHand;
}
