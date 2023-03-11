package com.example.msscpizzaservice.service.inventory;

import com.example.model.dto.PizzaInventoryDto;
import com.example.msscpizzaservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "localhost:8082",
        value = "PIZZA-INVENTORY",
        fallback = InventoryServiceFeignClientFailover.class,
        configuration = FeignClientConfig.class)
public interface InventoryServiceFeignClient {

    @GetMapping(path = "/api/v1/pizza/{pizzaId}/inventory")
    ResponseEntity<List<PizzaInventoryDto>> getOnHandInventory(@PathVariable Long pizzaId);
}
