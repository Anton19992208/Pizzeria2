package com.example.msscpizzaorder.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Customer extends BaseEntity {

    public Customer(Long id, Long version, LocalDate createdDate, LocalDate lastModifiedDate) {
        super(id, version, createdDate, lastModifiedDate);
    }

    private String customerName;

    private Long apiKey;

    @Builder.Default
    @OneToMany(mappedBy = "customer")
    private List<PizzaOrder> beerOrders = new ArrayList<>();

}
