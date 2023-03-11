package com.example.msscpizzaservice.service.inventory;

import com.example.model.dto.PizzaInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "INVENTORY-FAILOVER")
public interface InventoryFailoverFeignClient {

    @GetMapping(path = "/inventory-failover")
    ResponseEntity<List<PizzaInventoryDto>> getOnHandInventory();
}
