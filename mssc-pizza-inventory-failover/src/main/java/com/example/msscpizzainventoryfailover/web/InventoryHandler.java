package com.example.msscpizzainventoryfailover.web;

import com.example.model.PizzaInventoryDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class InventoryHandler {

    public Mono<ServerResponse> listInventory(ServerRequest request){
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(Mono.just(Arrays.asList(PizzaInventoryDto.builder()
                                .id(2L)
                                .pizzaId(Long.valueOf("0000-00000-00000"))
                                .quantityOnHand(999)
                                .createdDate(LocalDate.now())
                                .lastModifiedDate(LocalDate.now())
                        .build())), List.class);
    }
}
