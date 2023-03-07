package com.example.msscpizzaservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pizza {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Version
    private Long version;

    private String name;

    private String ingredients;

    private Integer size;

    private BigDecimal price;

    @Column(name = "upc", unique = true)
    private String upc;

    @Column(updatable = false)
    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    private Integer quantityOnHand;

    private Integer quantityToCook;
}
