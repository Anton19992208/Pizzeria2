package com.example.msscpizzaorder.sm.action;

import com.example.msscpizzaorder.domain.PizzaOrderEvent;
import com.example.msscpizzaorder.domain.PizzaOrderStatus;
import com.example.msscpizzaorder.service.PizzaOrderManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ValidationFailureAction implements Action<PizzaOrderStatus, PizzaOrderEvent> {

    @Override
    public void execute(StateContext<PizzaOrderStatus, PizzaOrderEvent> stateContext) {
        String pizzaOrderId = (String) stateContext.getMessage().getHeaders().get(PizzaOrderManagerImpl.ORDER_ID_HEADER);
        log.error("Compensating Transaction - Validation Failed: " + pizzaOrderId);
    }
}
