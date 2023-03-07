package com.example.msscpizzaservice.service;

import com.example.model.dto.PizzaDto;
import com.example.msscpizzaservice.domain.Pizza;
import com.example.msscpizzaservice.exception.NotFoundException;
import com.example.msscpizzaservice.mapper.PizzaMapper;
import com.example.msscpizzaservice.repository.PizzaRepository;
import com.example.msscpizzaservice.service.inventory.InventoryServiceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;
    private final InventoryServiceFeignClient inventoryServiceFeignClient;

    @Cacheable(cacheNames = "pizzaListCache")
    @Override
    public Page<PizzaDto> findAll(Pageable pageable, Boolean showQuantityOnHand) {
        if (showQuantityOnHand) {
            return pizzaRepository.findAll(pageable)
                    .map(pizzaMapper::pizzaToPizzaDtoWithInventory);
        } else {
            return pizzaRepository.findAll(pageable)
                    .map(pizzaMapper::pizzaToPizzaDto);
        }
    }

    @Cacheable(value = "pizzaCache")
    @Override
    public PizzaDto findById(Long id, Boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            return pizzaMapper.pizzaToPizzaDtoWithInventory(
                    pizzaRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException(id + ", Not Found."))
            );
        } else {
            return pizzaMapper.pizzaToPizzaDto(
                    pizzaRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException(id + ", Not Found."))
            );

        }
    }

    @Cacheable(value = "pizzaUpcCache")
    @Override
    public PizzaDto findByUpc(String upc) {
        return pizzaMapper.pizzaToPizzaDto(pizzaRepository.findByUpc(upc));
    }

    @Override
    public PizzaDto savePizza(PizzaDto pizzaDto) {
        return pizzaMapper.pizzaToPizzaDto(pizzaRepository.save(pizzaMapper.pizzaDtoToPizza(pizzaDto)));
    }

    @Override
    public PizzaDto updatePizza(Long id, PizzaDto pizzaDto) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id + ", Not Found."));

        pizza.setName(pizzaDto.getName());
        pizza.setPrice(pizzaDto.getPrice());
        pizza.setSize(pizzaDto.getSize());
        pizza.setIngredients(pizzaDto.getIngredients());
        return pizzaMapper.pizzaToPizzaDto(pizzaRepository.save(pizza));
    }
}
