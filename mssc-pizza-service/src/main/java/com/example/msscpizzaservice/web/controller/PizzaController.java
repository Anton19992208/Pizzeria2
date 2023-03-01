package com.example.msscpizzaservice.web.controller;

import com.example.model.dto.PizzaDto;
import com.example.model.dto.PizzaPagedList;
import com.example.msscpizzaservice.service.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class PizzaController {

    private final PizzaService pizzaService;

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    @GetMapping("pizza")
    public ResponseEntity<PizzaPagedList> findAllPizzas(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                        @RequestParam(value = "pizzaName", required = false) String pizzaName,
                                                        @RequestParam(value = "pizzaSize", required = false) Integer pizzaSize,
                                                        @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        PizzaPagedList pizzas = pizzaService.findAll(pizzaName, pizzaSize, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(pizzas, OK);
    }

    @GetMapping("pizza/{pizzaId}")
    public ResponseEntity<PizzaDto> findById(@PathVariable("pizzaId") Long pizzaId,
                                             @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        return new ResponseEntity<>(pizzaService.findById(pizzaId, showInventoryOnHand), OK);
    }

    @GetMapping("pizzaUpc/{upc}")
    public ResponseEntity<PizzaDto> findByUpc(@PathVariable("upc") String upc) {
        return new ResponseEntity<>(pizzaService.findByUpc(upc), OK);
    }

    @PostMapping(path = "pizza")
    public ResponseEntity saveNewPizza(@RequestBody @Validated PizzaDto pizzaDto) {
        return new ResponseEntity<>(pizzaService.savePizza(pizzaDto), CREATED);
    }

    @PutMapping("beer/{pizzaId}")
    public ResponseEntity updatePizzaById(@PathVariable("pizzaId") Long beerId, @RequestBody @Validated PizzaDto beerDto) {

        return new ResponseEntity<>(pizzaService.updatePizza(beerId, beerDto), HttpStatus.NO_CONTENT);
    }
}