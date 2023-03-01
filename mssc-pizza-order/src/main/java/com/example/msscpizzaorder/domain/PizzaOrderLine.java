package com.example.msscpizzaorder.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PizzaOrderLine extends BaseEntity{

    public PizzaOrderLine(Long id, Long version, LocalDate createdDate, LocalDate lastModifiedDate) {
        super(id, version, createdDate, lastModifiedDate);
    }

    @ManyToOne(fetch = LAZY)
    private PizzaOrder pizzaOrder;

    private Long pizzaId;
    private String upc;
    private Integer orderQuantity = 0;
    private Integer quantityAllocated = 0;
}
