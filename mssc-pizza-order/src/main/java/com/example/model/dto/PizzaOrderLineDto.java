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

    private Long id;

    private Integer version;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    private String upc;

    private String pizzaName;

    private Integer pizzaSize;

    private Long pizzaId;

    private Integer orderQuantity;

    private BigDecimal price;

    private Integer quantityAllocated;
}
