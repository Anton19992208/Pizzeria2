package com.example.msscpizzaservice.bootstrap;

import com.example.msscpizzaservice.domain.Pizza;
import com.example.msscpizzaservice.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PizzaLoader implements CommandLineRunner {

    private final PizzaRepository pizzaRepository;

    @Override
    public void run(String... args) throws Exception {
        if(pizzaRepository.count() == 0){
            loadPizzaObjects();
        }
    }

    private void loadPizzaObjects(){
        Pizza pizza1 = Pizza.builder()
                .name("Italia")
                .ingredients("Pepperoni, Onion...")
                .size(30)
                .quantityToCook(20)
                .quantityOnHand(10)
                .build();

        Pizza pizza2 = Pizza.builder()
                .name("FourCheeses")
                .ingredients("Lots of different cheese")
                .size(30)
                .quantityToCook(20)
                .quantityOnHand(10)
                .build();

        Pizza pizza3 = Pizza.builder()
                .name("For vegans")
                .ingredients("Lots of herbs")
                .size(30)
                .quantityToCook(20)
                .quantityOnHand(10)
                .build();

        pizzaRepository.save(pizza1);
        pizzaRepository.save(pizza2);
        pizzaRepository.save(pizza3);
    }
}
