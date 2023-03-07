package com.example.msscpizzaorder.service;

import com.example.model.dto.CustomerDto;
import com.example.model.dto.CustomerPagedList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

   Page<CustomerDto> listCustomers(Pageable pageable);
}
