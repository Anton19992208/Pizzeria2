package com.example.msscpizzaorder.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PizzaOrder extends BaseEntity {

    public PizzaOrder(Long id, Long version, LocalDate createdDate, LocalDate lastModifiedDate) {
        super(id, version, createdDate, lastModifiedDate);
    }

    private String customerRef;

    @ManyToOne(fetch = LAZY, cascade = REMOVE)
    private Customer customer;

    @Builder.Default
    @OneToMany(mappedBy = "pizzaOrder")
    private List<PizzaOrderLine> pizzaOrderLines = new ArrayList<>();

    private PizzaOrderStatus orderStatus = PizzaOrderStatus.NEW;
    private String orderStatusCallbackUrl;
}
