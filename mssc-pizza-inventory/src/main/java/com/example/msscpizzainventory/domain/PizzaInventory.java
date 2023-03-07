package com.example.msscpizzainventory.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PizzaInventory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Version
    private Long version;

    private Long pizzaId;

    private String upc;

    private Integer quantityOnHand = 0;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;


}
