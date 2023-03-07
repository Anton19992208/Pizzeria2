package com.example.msscpizzaorder.repository;

import com.example.msscpizzaorder.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByCustomerNameLike(String customerName);
}
