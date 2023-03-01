package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PizzaDto implements Serializable {

    static final long serialVersionUID = -5815500040065181210L;

    private Long id;

    private Integer version;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    @NotBlank
    private String pizzaName;

    @Max(40)
    private Integer pizzaSize;

    @Positive
    @NotNull
    private BigDecimal price;

    @NotNull
    private String upc;

    private Integer quantityOnHand;
    private Integer quantityToCook;
}
