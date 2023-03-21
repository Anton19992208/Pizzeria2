package com.example.msscpizzainventoryfailover.web;

import com.example.model.PizzaInventoryDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class InventoryHandler {

    public Mono<ServerResponse> listInventory(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(Mono.just(Arrays.asList(
                        PizzaInventoryDto.builder()
                                .id(2L)
                                .pizzaId(0L)
                                .quantityOnHand(999)
                                .createdDate(LocalDate.now())
                                .lastModifiedDate(LocalDate.now())
                                .build())), List.class);
    }


}
