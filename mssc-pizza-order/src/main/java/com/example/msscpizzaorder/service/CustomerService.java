package com.example.msscpizzaorder.service;

import com.example.model.dto.CustomerPagedList;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    CustomerPagedList listCustomers(Pageable pageable);
}
