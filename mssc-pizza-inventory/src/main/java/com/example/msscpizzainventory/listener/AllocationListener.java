package com.example.msscpizzainventory.listener;

import com.example.model.event.AllocateOrderResult;
import com.example.model.event.AllocateOrderRequest;
import com.example.msscpizzainventory.config.JmsConfig;
import com.example.msscpizzainventory.service.AllocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AllocationListener {

    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequest request){
        AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder();
        builder.pizzaOrderDto(request.getPizzaOrderDto());

        try{
            Boolean allocationResult = allocationService.allocateOrder(request.getPizzaOrderDto());

            if (allocationResult){
                builder.pendingInventory(false);
            } else {
                builder.pendingInventory(true);
            }

            builder.allocationError(false);
        } catch (Exception e){
            log.error("Allocation failed for Order Id:" + request.getPizzaOrderDto().getId());
            builder.allocationError(true);
        }
        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE,
                builder.build());

    }
}
