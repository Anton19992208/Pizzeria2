package com.example.model.dto;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PizzaOrderPagedList extends PageImpl<PizzaOrderDto> {

    public PizzaOrderPagedList(List<PizzaOrderDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PizzaOrderPagedList(List<PizzaOrderDto> content) {
        super(content);
    }
}
