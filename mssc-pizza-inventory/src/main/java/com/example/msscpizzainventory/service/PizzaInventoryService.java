package com.example.msscpizzainventory.service;

import com.example.model.dto.PizzaInventoryDto;

import java.util.List;

public interface PizzaInventoryService {

    List<PizzaInventoryDto> findByPizzaId(Long id);

}
