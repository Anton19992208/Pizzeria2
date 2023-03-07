package com.example.msscpizzaservice.service.inventory;

import com.example.model.dto.PizzaInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpMethod.GET;

@Profile("!local-discovery")
@Component
@Slf4j
@ConfigurationProperties(prefix = "sfg.pizzeria")
public class PizzaInventoryServiceRestTemplateImpl implements PizzaInventoryService{

    public static final String INVENTORY_PATH = "/api/v1/pizza/{pizzaId}/inventory";
    private final RestTemplate restTemplate;

    private String pizzaInventoryServiceHost;

    public void setPizzaInventoryServiceHost(String pizzaInventoryServiceHost) {
        this.pizzaInventoryServiceHost = pizzaInventoryServiceHost;
    }

    @Autowired
    public PizzaInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Integer getOnhandInventory(Long pizzaId) {

        log.debug("Calling Inventory Service");

        ResponseEntity<List<PizzaInventoryDto>> responseEntity = restTemplate
                .exchange(pizzaInventoryServiceHost + INVENTORY_PATH, GET, null,
                        new ParameterizedTypeReference<>() {
                        }, (Object) pizzaId);


        Integer onHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(PizzaInventoryDto::getQuantityOnHand)
                .sum();

        return onHand;
    }
}
