package com.example.model.evetns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateOrderResult {

    private Long orderId;
    private Boolean isValid;

}
