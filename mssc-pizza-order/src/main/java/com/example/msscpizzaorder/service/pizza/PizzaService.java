package com.example.msscpizzaorder.service.pizza;

import com.example.model.dto.PizzaDto;

import java.util.Optional;

public interface PizzaService {

    Optional<PizzaDto> findPizzaById(Long id);

    Optional<PizzaDto> findPizzaByUpc(String upc);
}
