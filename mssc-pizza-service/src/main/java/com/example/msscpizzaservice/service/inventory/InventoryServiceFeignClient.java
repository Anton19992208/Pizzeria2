package com.example.msscpizzaservice.service.inventory;

import com.example.model.dto.PizzaInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("inventory-service")
public interface InventoryServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = PizzaInventoryServiceRestTemplateImpl.INVENTORY_PATH)
    ResponseEntity<List<PizzaInventoryDto>> getOnHandInventory(@PathVariable Long pizzaId);
}
