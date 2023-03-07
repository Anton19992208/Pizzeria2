package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("version")
    private Integer version = null;

    @JsonProperty("createdDate")
    private LocalDate createdDate = null;

    @JsonProperty("lastModifiedDate")
    private LocalDate lastModifiedDate = null;
    private Long customerId;
    private String customerRef;
    private List<PizzaOrderLineDto> pizzaOrderLines;
    private String orderStatus;
    private String orderStatusCallbackUrl;
}
