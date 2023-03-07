package com.example.msscpizzaorder.service;

import com.example.model.dto.CustomerDto;
import com.example.model.dto.CustomerPagedList;
import com.example.msscpizzaorder.domain.Customer;
import com.example.msscpizzaorder.mapper.CustomerMapper;
import com.example.msscpizzaorder.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Page<CustomerDto> listCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customerMapper::customerToCustomerDto);
    }
}
