package com.example.msscpizzaorder.sm;

import com.example.msscpizzaorder.domain.PizzaOrder;
import com.example.msscpizzaorder.domain.PizzaOrderEvent;
import com.example.msscpizzaorder.domain.PizzaOrderStatus;
import com.example.msscpizzaorder.repository.PizzaOrderRepository;
import com.example.msscpizzaorder.service.PizzaOrderManagerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PizzaOrderStateChangeInterceptor extends StateMachineInterceptorAdapter<PizzaOrderStatus, PizzaOrderEvent> {

    private final PizzaOrderRepository pizzaOrderRepository;

    @Override
    public void postStateChange(State<PizzaOrderStatus, PizzaOrderEvent> state,
                                Message<PizzaOrderEvent> message,
                                Transition<PizzaOrderStatus, PizzaOrderEvent> transition,
                                StateMachine<PizzaOrderStatus, PizzaOrderEvent> stateMachine,
                                StateMachine<PizzaOrderStatus, PizzaOrderEvent> rootStateMachine) {
        log.debug("Pre-State Change");

        Optional.ofNullable(message)
                .flatMap(msg -> Optional.ofNullable((String) msg.getHeaders().getOrDefault(PizzaOrderManagerImpl.ORDER_ID_HEADER, " ")))
                .ifPresent(orderId -> {
                    log.debug("Saving state for order id: " + orderId + " Status: " + state.getId());

                    Optional<PizzaOrder> pizzaOrder = pizzaOrderRepository.findById(Long.valueOf(orderId));
                    pizzaOrder.ifPresent(stateId -> {
                        pizzaOrder.get().setOrderStatus(state.getId());

                    });
                    pizzaOrder.map(pizzaOrderRepository::saveAndFlush);
                });
    }
}