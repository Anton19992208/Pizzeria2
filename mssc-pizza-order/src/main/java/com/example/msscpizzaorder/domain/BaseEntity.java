package com.example.msscpizzaorder.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long version;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    public boolean isNew(){
        return this.id == null;
    }
}
