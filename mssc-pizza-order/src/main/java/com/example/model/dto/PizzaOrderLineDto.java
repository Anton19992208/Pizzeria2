package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PizzaOrderLineDto {

    private Long id = null;

    private Integer version = null;

    private LocalDate createdDate = null;

    private LocalDate lastModifiedDate = null;

    private String upc;

    private String pizzaName;

    private Integer pizzaSize;

    private Long pizzaId;

    private Integer orderQuantity = 0;

    private BigDecimal price;

    private Integer quantityAllocated;
}
