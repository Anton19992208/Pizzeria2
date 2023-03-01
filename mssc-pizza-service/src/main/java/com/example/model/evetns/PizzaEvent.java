package com.example.model.evetns;


import com.example.model.dto.PizzaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PizzaEvent implements Serializable {

    static final long serialVersionUID = -5815566940065181210L;

    private PizzaDto pizzaDto;
}
