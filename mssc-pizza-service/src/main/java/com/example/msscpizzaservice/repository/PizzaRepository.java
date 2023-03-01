package com.example.msscpizzaservice.repository;

import com.example.msscpizzaservice.domain.Pizza;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    Page<Pizza> findAllByName(String name, Pageable pageable);

    Page<Pizza> findAllBySize(Integer size, Pageable pageable);

    Page<Pizza> findAllByNameAndSize(String name, Integer size, Pageable pageable);

    Pizza findByUpc(String upc);

}
