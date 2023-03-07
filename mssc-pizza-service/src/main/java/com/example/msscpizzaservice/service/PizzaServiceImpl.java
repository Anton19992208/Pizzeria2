package com.example.msscpizzaservice.service;

import com.example.model.dto.PizzaDto;
import com.example.model.dto.PizzaPagedList;
import com.example.msscpizzaservice.domain.Pizza;
import com.example.msscpizzaservice.exception.NotFoundException;
import com.example.msscpizzaservice.mapper.PizzaMapper;
import com.example.msscpizzaservice.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;

    @Cacheable(cacheNames = "pizzaListCache")
    @Override
    public PizzaPagedList findAll(String pizzaName, Integer size, PageRequest of, Boolean showQuantityOnHand) {
        PizzaPagedList pizzaPagedList;
        Page<Pizza> pizzaPage;

        if (!ObjectUtils.isEmpty(pizzaName) && !ObjectUtils.isEmpty(size)) {
            pizzaPage = pizzaRepository.findAllByNameAndSize(pizzaName, size, of);
        } else if (!ObjectUtils.isEmpty(pizzaName) && ObjectUtils.isEmpty(size)) {
            pizzaPage = pizzaRepository.findAllByName(pizzaName, of);
        } else if (ObjectUtils.isEmpty(pizzaName) && !ObjectUtils.isEmpty(size)) {
            pizzaPage = pizzaRepository.findAllBySize(size, of);
        } else {
            pizzaPage = pizzaRepository.findAll(of);
        }

        if (showQuantityOnHand) {
            pizzaPagedList = new PizzaPagedList(
                    pizzaPage.getContent()
                            .stream()
                            .map(pizzaMapper::pizzaToPizzaDtoWithInventory)
                            .collect(toList()),
                    PageRequest
                            .of(pizzaPage.getPageable().getPageNumber(),
                                    pizzaPage.getPageable().getPageSize()),
                    pizzaPage.getTotalElements());

        } else {
            pizzaPagedList = new PizzaPagedList(
                    pizzaPage.getContent()
                            .stream()
                            .map(pizzaMapper::pizzaToPizzaDto)
                            .collect(toList()),
                    PageRequest
                            .of(pizzaPage.getPageable().getPageNumber(),
                                    pizzaPage.getPageable().getPageSize()),
                    pizzaPage.getTotalElements());

        }
        return pizzaPagedList;
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
