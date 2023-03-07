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

    private Long id;

    private Integer version;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    private Long customerId;

    private String customerRef;

    private List<PizzaOrderLineDto> pizzaOrderLines;

    private String orderStatus;

    private String orderStatusCallbackUrl;
}
