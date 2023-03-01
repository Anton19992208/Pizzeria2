package com.example.msscpizzaorder.sm;

import com.example.msscpizzaorder.domain.PizzaOrderEvent;
import com.example.msscpizzaorder.domain.PizzaOrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

import static com.example.msscpizzaorder.domain.PizzaOrderEvent.ALLOCATE_ORDER;
import static com.example.msscpizzaorder.domain.PizzaOrderEvent.ALLOCATION_FAILED;
import static com.example.msscpizzaorder.domain.PizzaOrderEvent.ALLOCATION_NO_INVENTORY;
import static com.example.msscpizzaorder.domain.PizzaOrderEvent.CANCEL_ORDER;
import static com.example.msscpizzaorder.domain.PizzaOrderEvent.PIZZAORDER_PICKED_UP;
import static com.example.msscpizzaorder.domain.PizzaOrderEvent.VALIDATE_ORDER;
import static com.example.msscpizzaorder.domain.PizzaOrderEvent.VALIDATION_FAILED;
import static com.example.msscpizzaorder.domain.PizzaOrderEvent.VALIDATION_PASSED;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.ALLOCATED;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.ALLOCATION_EXCEPTION;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.ALLOCATION_PENDING;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.CANCELLED;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.NEW;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.PENDING_INVENTORY;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.PICKED_UP;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.VALIDATED;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.VALIDATION_EXCEPTION;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.VALIDATION_PENDING;

@Configuration
@RequiredArgsConstructor
@EnableStateMachineFactory
public class PizzaOrderStateMachineConfig extends StateMachineConfigurerAdapter<PizzaOrderStatus, PizzaOrderEvent> {

    private final Action<PizzaOrderStatus, PizzaOrderEvent>  validateOrderAction;
    private final Action<PizzaOrderStatus, PizzaOrderEvent>  allocateOrderAction;
    private final Action<PizzaOrderStatus, PizzaOrderEvent>  validationFailureAction;;
    private final Action<PizzaOrderStatus, PizzaOrderEvent>  allocationFailureAction;
    private final Action<PizzaOrderStatus, PizzaOrderEvent>  deallocateOrderAction;

    @Override
    public void configure(StateMachineStateConfigurer<PizzaOrderStatus, PizzaOrderEvent> states) throws Exception {
        states.withStates()
                .initial(NEW)
                .states(EnumSet.allOf(PizzaOrderStatus.class))
                .end(PizzaOrderStatus.PICKED_UP)
                .end(PizzaOrderStatus.DELIVERED)
                .end(PizzaOrderStatus.CANCELLED)
                .end(PizzaOrderStatus.DELIVERY_EXCEPTION)
                .end(PizzaOrderStatus.ALLOCATION_EXCEPTION)
                .end(PizzaOrderStatus.VALIDATION_EXCEPTION);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<PizzaOrderStatus, PizzaOrderEvent> transitions) throws Exception {
        transitions.withExternal().source(NEW).target(VALIDATION_PENDING)
                .event(VALIDATE_ORDER)
                .action(validateOrderAction)
                .and()
                .withExternal().source(VALIDATION_PENDING).target(VALIDATED).event(VALIDATION_PASSED)
                .and()
                .withExternal().source(VALIDATION_PENDING).target(VALIDATION_EXCEPTION).event(VALIDATION_FAILED).action(validationFailureAction)
                .and()
                .withExternal().source(VALIDATION_PENDING).target(CANCELLED).event(CANCEL_ORDER)
                .and()
                .withExternal().source(VALIDATED).target(ALLOCATION_PENDING).event(ALLOCATE_ORDER)
                .action(allocateOrderAction)
                .and()
                .withExternal().source(VALIDATED).target(CANCELLED).event(CANCEL_ORDER)
                .and()
                .withExternal().source(ALLOCATION_PENDING).target(ALLOCATED).event(ALLOCATE_ORDER)
                .and()
                .withExternal().source(ALLOCATION_PENDING).target(CANCELLED).event(CANCEL_ORDER)
                .and()
                .withExternal().source(ALLOCATION_PENDING).target(ALLOCATION_EXCEPTION).event(ALLOCATION_FAILED).action(allocationFailureAction)
                .and()
                .withExternal().source(ALLOCATION_PENDING).target(PENDING_INVENTORY).event(ALLOCATION_NO_INVENTORY)
                .and()
                .withExternal().source(ALLOCATED).target(PICKED_UP).event(PIZZAORDER_PICKED_UP)
                .and()
                .withExternal().source(ALLOCATED).target(CANCELLED).event(CANCEL_ORDER).action(deallocateOrderAction);
    }
}
