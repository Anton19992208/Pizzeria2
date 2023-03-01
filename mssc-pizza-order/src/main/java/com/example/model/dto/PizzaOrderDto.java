package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PizzaOrderDto {

    private Long id = null;

    private Integer version = null;

    private LocalDate createdDate = null;

    private LocalDate lastModifiedDate = null;

    private Long customerId;

    private String customerRef;

    private List<PizzaOrderLineDto> pizzaOrderLines;

    private String orderStatus;

    private String orderStatusCallbackUrl;
}
