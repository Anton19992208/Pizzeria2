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
public class PizzaOrderLineDto  {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("version")
    private Long version;

    private String name;

    private String ingredients;

    private Integer size;

    private BigDecimal price;

    private String upc;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    private Integer orderQuantity = 0;
}
