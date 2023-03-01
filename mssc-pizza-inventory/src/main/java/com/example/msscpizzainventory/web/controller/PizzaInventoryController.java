package com.example.msscpizzainventory.web.controller;

import com.example.model.dto.PizzaInventoryDto;
import com.example.msscpizzainventory.service.PizzaInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/pizza/")
@RequiredArgsConstructor
@Slf4j
public class PizzaInventoryController {

    private final PizzaInventoryService pizzaInventoryService;

    @GetMapping("{pizzaId}/inventory")
    List<PizzaInventoryDto> findAllPizzasById(@PathVariable("pizzaId") Long id){
        log.debug("Finding Inventory for beerId:" + id);

        return pizzaInventoryService.findByPizzaId(id);
    }
}
