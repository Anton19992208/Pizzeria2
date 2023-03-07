package com.example.msscpizzaorder.sm.action;

import com.example.model.events.DeallocateOrderRequest;
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
@RequiredArgsConstructor
@Component
public class DeallocateOrderAction implements Action<PizzaOrderStatus, PizzaOrderEvent> {

    private final JmsTemplate jmsTemplate;
    private final PizzaOrderRepository pizzaOrderRepository;
    private final PizzaOrderMapper pizzaOrderMapper;

    @Override
    public void execute(StateContext<PizzaOrderStatus, PizzaOrderEvent> stateContext) {
        String pizzaOrderId = (String) stateContext.getMessage().getHeaders().get(PizzaOrderManagerImpl.ORDER_ID_HEADER);

        Optional<PizzaOrder> pizzaOrderOptional = pizzaOrderRepository.findById(Long.valueOf(pizzaOrderId));

        log.debug("Sent Allocation Request for order id: " + pizzaOrderId);

        pizzaOrderOptional.ifPresentOrElse(pizzaOrder -> {
            jmsTemplate.convertAndSend(JmsConfig.DEALLOCATE_ORDER_QUEUE,
                    DeallocateOrderRequest.builder()
                            .pizzaOrderDto(pizzaOrderMapper.pizzaOrderToPizzaOrderDto(pizzaOrder))
                            .build());
            log.debug("Sent Deallocation Request for order id: " + pizzaOrderId);
        }, () -> log.error("Beer Order Not Found!"));
    }
}
