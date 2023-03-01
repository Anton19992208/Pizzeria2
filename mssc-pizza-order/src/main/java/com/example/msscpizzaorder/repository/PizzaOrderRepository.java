package com.example.msscpizzaorder.repository;

import com.example.msscpizzaorder.domain.Customer;
import com.example.msscpizzaorder.domain.PizzaOrder;
import com.example.msscpizzaorder.domain.PizzaOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PizzaOrderRepository extends JpaRepository<PizzaOrder, Long> {

    Page<PizzaOrder> findAllByCustomer(Customer customer, Pageable pageable);

    List<PizzaOrder> findAllByOrderStatus(PizzaOrderStatus pizzaOrderStatus);

    PizzaOrder save(PizzaOrder pizzaOrder);


}
