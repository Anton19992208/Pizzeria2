package com.example.msscpizzaorder.web.controller;

import com.example.model.dto.CustomerDto;
import com.example.model.dto.PageResponse;
import com.example.msscpizzaorder.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers/")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public PageResponse<CustomerDto> listCustomers(Pageable pageable){
       Page<CustomerDto> customer = customerService.listCustomers(pageable);

       return PageResponse.of(customer);
    }

}
