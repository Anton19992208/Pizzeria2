package com.example.msscpizzaorder.service;

import com.example.model.dto.PizzaOrderDto;
import com.example.msscpizzaorder.domain.PizzaOrder;
import com.example.msscpizzaorder.domain.PizzaOrderEvent;
import com.example.msscpizzaorder.domain.PizzaOrderLine;
import com.example.msscpizzaorder.domain.PizzaOrderStatus;
import com.example.msscpizzaorder.repository.PizzaOrderRepository;
import com.example.msscpizzaorder.sm.PizzaOrderStateChangeInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.msscpizzaorder.domain.PizzaOrderEvent.ALLOCATE_ORDER;
import static com.example.msscpizzaorder.domain.PizzaOrderEvent.ALLOCATION_NO_INVENTORY;
import static com.example.msscpizzaorder.domain.PizzaOrderEvent.ALLOCATION_SUCCESS;
import static com.example.msscpizzaorder.domain.PizzaOrderEvent.VALIDATE_ORDER;
import static com.example.msscpizzaorder.domain.PizzaOrderEvent.VALIDATION_FAILED;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.ALLOCATED;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.ALLOCATION_PENDING;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.NEW;
import static com.example.msscpizzaorder.domain.PizzaOrderStatus.PENDING_INVENTORY;

@Slf4j
@Service
@RequiredArgsConstructor
public class PizzaOrderManagerImpl implements PizzaOrderManager {

    public static final String ORDER_ID_HEADER = "ORDER_ID_HEADER";

    private final StateMachineFactory<PizzaOrderStatus, PizzaOrderEvent> stateMachineFactory;
    private final PizzaOrderRepository pizzaOrderRepository;
    private final PizzaOrderStateChangeInterceptor pizzaOrderStateChangeInterceptor;

    @Transactional
    @Override
    public PizzaOrder newPizzaOrder(PizzaOrder pizzaOrder) {
        pizzaOrder.setOrderStatus(NEW);

        PizzaOrder savedPizzaOrder = pizzaOrderRepository.saveAndFlush(pizzaOrder);
        sendPizzaOrderEvent(savedPizzaOrder, VALIDATE_ORDER);
        return savedPizzaOrder;
    }

    @Override
    public void processValidationResult(Long pizzaOrderId, Boolean isValid) {
        log.debug("Process validation for pizzaOrderId" + pizzaOrderId + "isValid?: " + isValid);

        PizzaOrder pizzaOrderFound = pizzaOrderRepository.getReferenceById(pizzaOrderId);

        if (isValid) {
            sendPizzaOrderEvent(pizzaOrderFound, VALIDATE_ORDER);

            awaitForStatus(pizzaOrderId, ALLOCATION_PENDING);

            PizzaOrder validatedOrder = pizzaOrderRepository.findById(pizzaOrderId).get();

            sendPizzaOrderEvent(validatedOrder, ALLOCATE_ORDER);
        } else {
            sendPizzaOrderEvent(pizzaOrderFound, VALIDATION_FAILED);
        }
    }

    @Override
    public void pizzaOrderAllocationPassed(PizzaOrderDto pizzaOrderDto) {
        getOptionalPizzaOrder(pizzaOrderDto, ALLOCATION_SUCCESS, ALLOCATED);
    }

    @Override
    public void pizzaOrderAllocationPendingInventory(PizzaOrderDto pizzaOrderDto) {
        getOptionalPizzaOrder(pizzaOrderDto, ALLOCATION_NO_INVENTORY, PENDING_INVENTORY);
    }

    private void getOptionalPizzaOrder(PizzaOrderDto pizzaOrderDto,
                                       PizzaOrderEvent allocationNoInventory,
                                       PizzaOrderStatus pendingInventory) {
        Optional<PizzaOrder> pizzaOrderOptional = Optional.of(pizzaOrderRepository.getReferenceById(pizzaOrderDto.getId()));

        pizzaOrderOptional.ifPresentOrElse(pizzaOrder -> {
            sendPizzaOrderEvent(pizzaOrder, allocationNoInventory);
            awaitForStatus(pizzaOrder.getId(), pendingInventory);
            updateAllocatedQty(pizzaOrderDto);
        }, () -> log.error("Order Id Not Found: " + pizzaOrderDto.getId()));
    }

    @Override
    public void pizzaOrderAllocationFailed(PizzaOrderDto pizzaOrderDto) {
        Optional<PizzaOrder> pizzaOrderOptional = Optional.of(pizzaOrderRepository.getReferenceById(pizzaOrderDto.getId()));

        pizzaOrderOptional.ifPresentOrElse(pizzaOrder -> {
            sendPizzaOrderEvent(pizzaOrder, PizzaOrderEvent.ALLOCATION_FAILED);
        }, () -> log.error("Order Not Found. Id: " + pizzaOrderDto.getId()));
    }

    @Override
    public void pizzaOrderPickedUp(Long id) {
        Optional<PizzaOrder> pizzaOrderOptional = Optional.of(pizzaOrderRepository.getReferenceById(id));

        pizzaOrderOptional.ifPresentOrElse(pizzaOrder -> {
            sendPizzaOrderEvent(pizzaOrder, PizzaOrderEvent.PIZZAORDER_PICKED_UP);
        }, () -> log.error("Order Not Found. Id: " + id));
    }

    @Override
    public void cancelOrder(Long id) {
        pizzaOrderRepository.findById(id).ifPresentOrElse(pizzaOrder -> {
            sendPizzaOrderEvent(pizzaOrder, PizzaOrderEvent.CANCEL_ORDER);
        }, () -> log.error("Order Not Found. Id: " + id));
    }

    private void updateAllocatedQty(PizzaOrderDto pizzaOrderDto) {
        Optional<PizzaOrder> allocatedOrderOptional = Optional.of(pizzaOrderRepository.getReferenceById(pizzaOrderDto.getId()));

        allocatedOrderOptional.ifPresentOrElse(allocatedOrder -> {
            allocatedOrder.getPizzaOrderLines().forEach(pizzaOrderLine -> {
                pizzaOrderDto.getPizzaOrderLines().forEach(pizzaOrderLineDto -> {
                    if (pizzaOrderLine.getId().equals(pizzaOrderLineDto.getId())) {
                        pizzaOrderLine.setQuantityAllocated(pizzaOrderLineDto.getQuantityAllocated());
                    }
                });
            });

            pizzaOrderRepository.saveAndFlush(allocatedOrder);
        }, () -> log.error("Order Not Found. Id: " + pizzaOrderDto.getId()));
    }

    private void awaitForStatus(Long pizzaId, PizzaOrderStatus pizzaOrderStatus) {

        AtomicBoolean found = new AtomicBoolean(false);
        AtomicInteger loopCount = new AtomicInteger(0);

        while (!found.get()) {
            if (loopCount.incrementAndGet() > 10) {
                found.set(true);
                log.debug("Loop Retries exceeded");
            }

            Optional.of(pizzaOrderRepository.getReferenceById(pizzaId)).ifPresentOrElse(pizzaOrder -> {
                if (pizzaOrder.getOrderStatus().equals(pizzaOrderStatus)) {
                    found.set(true);
                    log.debug("Order Found");
                } else {
                    log.debug("Order Status Not Equal. Expected: " + pizzaOrderStatus.name() + " Found: " + pizzaOrder.getOrderStatus().name());
                }
            }, () -> log.error("Order Id Not Found"));

            if (!found.get()) {
                try {
                    log.debug("Sleeping for retry");
                    Thread.sleep(100);
                } catch (Exception ignored) {

                }
            }
        }
    }

    private void sendPizzaOrderEvent(PizzaOrder pizzaOrder, PizzaOrderEvent pizzaOrderEvent) {
        StateMachine<PizzaOrderStatus, PizzaOrderEvent> sm = build(pizzaOrder);

        Message msg = MessageBuilder.withPayload(pizzaOrderEvent)
                .setHeader(ORDER_ID_HEADER, pizzaOrder.getId().toString())
                .build();


        sm.sendEvent(msg);
    }

    private StateMachine<PizzaOrderStatus, PizzaOrderEvent> build(PizzaOrder pizzaOrder) {
        StateMachine<PizzaOrderStatus, PizzaOrderEvent> sm = stateMachineFactory.getStateMachine();

        sm.stop();

        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(pizzaOrderStateChangeInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(pizzaOrder.getOrderStatus(), null, null, null));
                });

        sm.start();

        return sm;
    }
}
