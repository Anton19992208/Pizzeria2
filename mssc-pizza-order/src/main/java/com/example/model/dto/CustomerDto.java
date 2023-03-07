package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private Long id = null;

    private Integer version = null;

    private LocalDate createdDate = null;

    private LocalDate lastModifiedDate = null;

    private String name;

}
