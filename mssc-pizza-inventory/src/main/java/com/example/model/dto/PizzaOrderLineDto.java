package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("version")
    private Integer version = null;

    @JsonProperty("createdDate")
    private LocalDate createdDate = null;

    @JsonProperty("lastModifiedDate")
    private LocalDate lastModifiedDate = null;
    private String upc;
    private String pizzaName;
    private Integer size;
    private Long pizzaId;
    private Integer orderQuantity = 0;
    private BigDecimal price;
    private Integer quantityAllocated;
}
