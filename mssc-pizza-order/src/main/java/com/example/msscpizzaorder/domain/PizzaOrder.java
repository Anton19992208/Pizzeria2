package com.example.msscpizzaorder.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static org.hibernate.annotations.FetchMode.JOIN;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PizzaOrder extends BaseEntity {

    @Builder
    public PizzaOrder(Long id, Long version, LocalDate createdDate, LocalDate lastModifiedDate, Customer customer,
                      PizzaOrderStatus orderStatus, Set<PizzaOrderLine> pizzaOrderLines, String orderStatusCallbackUrl,
                      String customerRef) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerRef = customerRef;
        this.customer = customer;
        this.orderStatus = orderStatus;
        this.pizzaOrderLines = pizzaOrderLines;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
    }

    private String customerRef;

    @ManyToOne(fetch = LAZY, cascade = REMOVE)
    private Customer customer;

    @OneToMany(mappedBy = "pizzaOrder")
    @Fetch(JOIN)
    private Set<PizzaOrderLine> pizzaOrderLines;

    @Enumerated(STRING)
    private PizzaOrderStatus orderStatus;

    private String orderStatusCallbackUrl;

    public void addOrderLine(PizzaOrderLine pizzaOrderLine){
        pizzaOrderLines.add(pizzaOrderLine);
        pizzaOrderLine.setPizzaOrder(this);
    }
}
