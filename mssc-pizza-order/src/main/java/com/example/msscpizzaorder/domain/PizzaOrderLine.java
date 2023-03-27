package com.example.msscpizzaorder.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PizzaOrderLine extends BaseEntity{

    @Builder
    public PizzaOrderLine(Long id, Long version, LocalDate createdDate, LocalDate lastModifiedDate, PizzaOrder pizzaOrder,
                          Long pizzaId, Integer orderQuantity, Integer quantityAllocated, String upc) {
        super(id, version, createdDate, lastModifiedDate);
        this.pizzaOrder = pizzaOrder;
        this.pizzaId = pizzaId;
        this.upc = upc;
        this.orderQuantity = orderQuantity;
        this.quantityAllocated = quantityAllocated;
    }

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private PizzaOrder pizzaOrder;

    private Long pizzaId;

    private String upc;

    private Integer orderQuantity;

    private Integer quantityAllocated;

    public void setPizzaOrder(PizzaOrder pizzaOrder){
        this.pizzaOrder = pizzaOrder;
        this.pizzaOrder.getPizzaOrderLines().add(this);
    }

}
