package com.example.msscpizzaorder.bootstrap;

import com.example.msscpizzaorder.domain.Customer;
import com.example.msscpizzaorder.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PizzaOrderBootStrap implements CommandLineRunner {

    public static final String PIZZA_1_UPC = "0631234200036";
    public static final String PIZZA_2_UPC = "0631234300019";
    public static final String PIZZA_3_UPC = "0083783375213";

    public static final String TASTING_ROOM = "Tasting Room";
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        if (customerRepository.findAllByCustomerNameLike(PizzaOrderBootStrap.TASTING_ROOM).isEmpty()) {
            loadCustomerData();
        }

    }

    private void loadCustomerData() {
        Customer savedCustomer = customerRepository.saveAndFlush(Customer.builder()
                .customerName(TASTING_ROOM)
                .apiKey(12L)
                .build());

        log.debug("Tasting Room Customer Id: " + savedCustomer.getId().toString());
    }
}
