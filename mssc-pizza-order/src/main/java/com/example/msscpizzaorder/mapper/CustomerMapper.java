package com.example.msscpizzaorder.mapper;

import com.example.model.dto.CustomerDto;
import com.example.msscpizzaorder.domain.Customer;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDto customerDto);

    CustomerDto customerToCustomerDto(Customer customer);

}
