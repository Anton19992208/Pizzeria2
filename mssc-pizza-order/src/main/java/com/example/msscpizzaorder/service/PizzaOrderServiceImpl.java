package com.example.msscpizzaorder.service;

import com.example.model.dto.PizzaOrderDto;
import com.example.msscpizzaorder.domain.Customer;
import com.example.msscpizzaorder.domain.PizzaOrder;
import com.example.msscpizzaorder.domain.PizzaOrderStatus;
import com.example.msscpizzaorder.mapper.PizzaOrderMapper;
import com.example.msscpizzaorder.repository.CustomerRepository;
import com.example.msscpizzaorder.repository.PizzaOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PizzaOrderServiceImpl implements PizzaOrderService {

    private final PizzaOrderRepository pizzaOrderRepository;
    private final CustomerRepository customerRepository;
    private final PizzaOrderMapper pizzaOrderMapper;
    private final PizzaOrderManager pizzaOrderManager;

    @Override
    public Page<PizzaOrderDto> listOrders(Long customerId, Pageable pageable) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        return customerOptional.map(customer -> pizzaOrderRepository.findAllByCustomer(customer, pageable)
                .map(pizzaOrderMapper::pizzaOrderToPizzaOrderDto)).orElseGet(Page::empty);
    }

    @Override
    public PizzaOrderDto placeOrder(Long customerId, PizzaOrderDto pizzaOrderDto) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            PizzaOrder pizzaOrder = pizzaOrderMapper.pizzaOrderDtoToPizzaOrder(pizzaOrderDto);
            pizzaOrder.setId(null);
            pizzaOrder.setOrderStatus(PizzaOrderStatus.NEW);
            pizzaOrder.setCustomer(customerOptional.get());

            pizzaOrder.getPizzaOrderLines().forEach(line -> line.setPizzaOrder(pizzaOrder));

            PizzaOrder savedPizzaOrder = pizzaOrderRepository.save(pizzaOrder);

            log.debug("Saved Pizza Order: " + pizzaOrder.getId());

            return pizzaOrderMapper.pizzaOrderToPizzaOrderDto(savedPizzaOrder);

        }

        return null;
    }

    @Override
    public PizzaOrderDto getOrderById(Long customerId, Long orderId) {
        return pizzaOrderMapper.pizzaOrderToPizzaOrderDto(getOrder(customerId, orderId));
    }

    @Override
    public void pickupOrder(Long orderId) {
        pizzaOrderManager.pizzaOrderPickedUp(orderId);
    }

    private PizzaOrder getOrder(Long customerId, Long orderId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            Optional<PizzaOrder> pizzaOrderOptional = pizzaOrderRepository.findById(orderId);

            if (pizzaOrderOptional.isPresent()) {
                PizzaOrder pizzaOrder = pizzaOrderOptional.get();

                if (pizzaOrder.getCustomer().getId().equals(customerId)) {
                    return pizzaOrder;
                }
            }

            throw new RuntimeException("Pizza Order Not Found");
        }

        throw new RuntimeException("Customer Not Found");
    }
}
