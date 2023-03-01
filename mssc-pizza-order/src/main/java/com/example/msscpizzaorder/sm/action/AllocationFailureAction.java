package com.example.msscpizzaorder.sm.action;

import com.example.model.events.AllocateFailureEvent;
import com.example.msscpizzaorder.config.JmsConfig;
import com.example.msscpizzaorder.domain.PizzaOrderEvent;
import com.example.msscpizzaorder.domain.PizzaOrderStatus;
import com.example.msscpizzaorder.service.PizzaOrderManagerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AllocationFailureAction implements Action<PizzaOrderStatus, PizzaOrderEvent> {

    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<PizzaOrderStatus, PizzaOrderEvent> stateContext) {
        String pizzaOrderId = (String) stateContext.getMessage().getHeaders().get(PizzaOrderManagerImpl.ORDER_ID_HEADER);

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_FAILURE_QUEUE,
                AllocateFailureEvent.builder()
                        .orderId(Long.valueOf(pizzaOrderId))
                        .build());

        log.debug("Sent Allocation failure to queue for order id " + pizzaOrderId);
    }
}
