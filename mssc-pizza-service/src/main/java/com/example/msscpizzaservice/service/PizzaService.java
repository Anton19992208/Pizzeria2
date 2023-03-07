package com.example.msscpizzaservice.service;

import com.example.model.dto.PizzaDto;
import com.example.model.dto.PizzaPagedList;
import org.springframework.data.domain.PageRequest;

public interface PizzaService {

    PizzaPagedList findAll(String pizzaName, Integer size, PageRequest of, Boolean showQuantityOnHand);

    PizzaDto findById(Long id, Boolean showInventoryOnHand);

    PizzaDto findByUpc(String upc);

    PizzaDto savePizza(PizzaDto pizzaDto);

    PizzaDto updatePizza(Long id, PizzaDto pizzaDto);

}
