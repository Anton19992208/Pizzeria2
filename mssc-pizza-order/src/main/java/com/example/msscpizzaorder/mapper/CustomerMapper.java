package com.example.msscpizzaorder.mapper;

import com.example.model.dto.CustomerDto;
import com.example.msscpizzaorder.domain.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDto customerDto);

    CustomerDto customerDtoToCustomer(Customer customer);
}
