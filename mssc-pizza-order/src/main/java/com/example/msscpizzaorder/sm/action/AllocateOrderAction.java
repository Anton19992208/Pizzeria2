package com.example.msscpizzaorder.sm.action;

import com.example.model.events.AllocateOrderRequest;
import com.example.msscpizzaorder.config.JmsConfig;
import com.example.msscpizzaorder.domain.PizzaOrder;
import com.example.msscpizzaorder.domain.PizzaOrderEvent;
import com.example.msscpizzaorder.domain.PizzaOrderStatus;
import com.example.msscpizzaorder.mapper.PizzaOrderMapper;
import com.example.msscpizzaorder.repository.PizzaOrderRepository;
import com.example.msscpizzaorder.service.PizzaOrderManagerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllocateOrderAction implements Action<PizzaOrderStatus, PizzaOrderEvent> {

    private final JmsTemplate jmsTemplate;
    private final PizzaOrderRepository pizzaOrderRepository;
    private final PizzaOrderMapper pizzaOrderMapper;

    @Override
    public void execute(StateContext<PizzaOrderStatus, PizzaOrderEvent> stateContext) {
        String pizzaOrderId = (String) stateContext.getMessage().getHeaders().get(PizzaOrderManagerImpl.ORDER_ID_HEADER);

        Optional<PizzaOrder> pizzaOrderOptional = pizzaOrderRepository.findById(Long.valueOf(pizzaOrderId));

        log.debug("Send allocation request for orderId" + pizzaOrderId);

        pizzaOrderOptional.ifPresentOrElse(pizzaOrder -> {
            jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_QUEUE,
                    AllocateOrderRequest.builder()
                            .pizzaOrderDto(pizzaOrderMapper.pizzaOrderToPizzaOrderDto(pizzaOrder))
                            .build());
            log.debug("Sent Allocation Request for order id: " + pizzaOrderId);
        }, () -> log.error("PizzaOrder NotFound"));
    }

}