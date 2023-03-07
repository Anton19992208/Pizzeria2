package com.example.msscpizzaorder.repository;

import com.example.msscpizzaorder.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByCustomerNameLike(String customerName);

    Page<Customer> findAllByCustomerNameLike(String customerName, Pageable pageable);
}
