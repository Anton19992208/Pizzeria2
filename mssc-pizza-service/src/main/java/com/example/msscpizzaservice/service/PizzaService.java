package com.example.msscpizzaservice.service;

import com.example.model.dto.PizzaDto;
import com.example.model.dto.PizzaPagedList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PizzaService {

    Page<PizzaDto> findAll(Pageable pageable, Boolean quantityOnHand);

    PizzaDto findById(Long id, Boolean showInventoryOnHand);

    PizzaDto findByUpc(String upc);

    PizzaDto savePizza(PizzaDto pizzaDto);

    PizzaDto updatePizza(Long id, PizzaDto pizzaDto);

}
