package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PizzaDto implements Serializable {

    static final long serialVersionUID = -5815566940065181210L;

    private Long id;

    private Long version;

    private String name;

    private String ingredients;

    @Max(40)
    @Positive
    private Integer size;

    private BigDecimal price;

    private String upc;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    private Integer quantityOnHand;

    private Integer quantityToCook;

}
