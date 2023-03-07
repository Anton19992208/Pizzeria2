package com.example.msscpizzainventory.repository;

import com.example.msscpizzainventory.domain.PizzaInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaInventoryRepository extends JpaRepository<PizzaInventory, Long> {

    //todo: Optional
    List<PizzaInventory> findAllByPizzaId(Long id);
    List<PizzaInventory> findAllByUpc(String upc);
}
