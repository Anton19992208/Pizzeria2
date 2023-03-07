package com.example.msscpizzaorder.service.pizza;

import com.example.model.dto.PizzaDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@ConfigurationProperties(prefix = "sfg.pizzeria")
@Service
public class PizzaServiceImpl implements PizzaService{

    public final static String PIZZA_PATH_V1 = "/api/v1/pizza/";
    public final static String PIZZA_UPC_PATH_V1 = "/api/v1/pizzaUpc/";
    private final RestTemplate restTemplate;

    private String pizzaServiceHost;

    public PizzaServiceImpl(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setPizzaServiceHost(String pizzaServiceHost) {
        this.pizzaServiceHost = pizzaServiceHost;
    }

    @Override
    public Optional<PizzaDto> findPizzaById(Long id) {
        return Optional.of(restTemplate.getForObject(pizzaServiceHost + PIZZA_PATH_V1 + id.toString(), PizzaDto.class));
    }

    @Override
    public Optional<PizzaDto> findPizzaByUpc(String upc) {
        return Optional.of(restTemplate.getForObject(pizzaServiceHost + PIZZA_UPC_PATH_V1 + upc, PizzaDto.class));
    }
}
