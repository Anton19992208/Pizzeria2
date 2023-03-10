package com.example.model.dto;

import com.example.msscpizzaservice.domain.Pizza;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PizzaPagedList extends PageImpl<PizzaDto> {


    public PizzaPagedList(List<PizzaDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PizzaPagedList(List<PizzaDto> content) {
        super(content);
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PizzaPagedList (@JsonProperty("content") List<PizzaDto> content,
                           @JsonProperty("number") int number,
                           @JsonProperty("size") int size,
                           @JsonProperty("totalElements") Long totalElements,
                           @JsonProperty("pageable") JsonNode pageable,
                           @JsonProperty("last") boolean last,
                           @JsonProperty("totalPages") int totalPages,
                           @JsonProperty("sort") JsonNode sort,
                           @JsonProperty("first") boolean first,
                           @JsonProperty("numberOfElements") int numberOfElements){
        super(content, PageRequest.of(number, size), totalElements);
    }
}
