package com.example.msscpizzaorder;

import com.example.model.dto.PizzaDto;
import com.example.msscpizzaorder.domain.Customer;
import com.example.msscpizzaorder.domain.PizzaOrder;
import com.example.msscpizzaorder.domain.PizzaOrderLine;
import com.example.msscpizzaorder.repository.CustomerRepository;
import com.example.msscpizzaorder.repository.PizzaOrderRepository;
import com.example.msscpizzaorder.service.PizzaOrderManager;
import com.example.msscpizzaorder.service.pizza.PizzaServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.example.msscpizzaorder.domain.PizzaOrderStatus.ALLOCATED;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PizzaOrderManagerIT {

    @Autowired
    PizzaOrderManager pizzaOrderManager;

    @Autowired
    PizzaOrderRepository pizzaOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private static WireMockServer wireMockServer;

    Customer testCustomer;

    Long pizzaId = new Random().nextLong();


    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
                .dynamicPort());

        wireMockServer.start();
    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }

    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.save(Customer.builder()
                .customerName("Test Customer")
                .build());
    }

    @Test
    void testWireMock() {
        System.out.println(wireMockServer.port());
        assertTrue(wireMockServer.isRunning());
        System.out.println(wireMockServer.listAllStubMappings());
    }


    @Transactional 
    @Test
    void testNewToAllocate() throws JsonProcessingException {
        PizzaDto pizzaDto = PizzaDto.builder()
                .id(pizzaId)
                .upc("12345")
                .name("jdjdjd")
                .price(new BigDecimal(123))
                .ingredients("dkdkdkkd")
                .build();

        wireMockServer.stubFor(WireMock.get(urlPathEqualTo(PizzaServiceImpl.PIZZA_UPC_PATH_V1 + "12345"))
                .willReturn(aResponse()
                        .withBody(objectMapper.writeValueAsString(pizzaDto))));

        PizzaOrder pizzaOrder = createPizzaOrder();
        PizzaOrder savedPizzaOrder = pizzaOrderManager.newPizzaOrder(pizzaOrder);
        assertNotNull(savedPizzaOrder);
        PizzaOrder savedPizzaOrder2 = pizzaOrderRepository.findById(savedPizzaOrder.getId()).get();
        System.out.println(savedPizzaOrder2.getOrderStatus());

//        await().untilAsserted(() -> {
//            PizzaOrder foundOrder = pizzaOrderRepository.findById(pizzaOrder.getId()).get();
//
//            assertEquals(PizzaOrderStatus.ALLOCATED, foundOrder.getOrderStatus());
//        });
//
//
        assertEquals(ALLOCATED, savedPizzaOrder.getOrderStatus());

    }

    public PizzaOrder createPizzaOrder() {
        Set<PizzaOrderLine> lines = new HashSet<>();
        lines.add(PizzaOrderLine.builder()
                .pizzaId(pizzaId)
                .upc("12345")
                .orderQuantity(1)
                .build());

        return PizzaOrder.builder()
                .customer(testCustomer)
//                .pizzaOrderLines(lines)
                .build();
    }
}
