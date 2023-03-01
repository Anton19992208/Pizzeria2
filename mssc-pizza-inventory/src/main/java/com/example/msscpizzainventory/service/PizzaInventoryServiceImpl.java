package com.example.msscpizzainventory.service;

import com.example.model.dto.PizzaInventoryDto;
import com.example.msscpizzainventory.domain.PizzaInventory;
import com.example.msscpizzainventory.mapper.PizzaInventoryMapper;
import com.example.msscpizzainventory.repository.PizzaInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PizzaInventoryServiceImpl implements PizzaInventoryService{

    private final PizzaInventoryRepository pizzaInventoryRepository;
    private final PizzaInventoryMapper pizzaInventoryMapper;

    @Override
    public List<PizzaInventoryDto> findByPizzaId(Long id) {
        return pizzaInventoryRepository.findAllByPizzaId(id)
                .stream()
                .map(pizzaInventoryMapper::pizzaInventoryToPizzaInventoryDto)
                .collect(toList());
    }

}
