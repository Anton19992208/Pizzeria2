package com.example.msscpizzaorder.testcomponents;

import com.example.model.events.AllocateOrderRequest;
import com.example.model.events.AllocateOrderResult;
import com.example.msscpizzaorder.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PizzaOrderAllocationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(Message msg){
        AllocateOrderRequest request = (AllocateOrderRequest) msg.getPayload();

        System.out.println("#######################################3LOCATE________________________________");

        request.getPizzaOrderDto().getPizzaOrderLines().forEach(pizzaOrderLoneDto ->{
            pizzaOrderLoneDto.setQuantityAllocated(pizzaOrderLoneDto.getOrderQuantity());
        });

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE,
                AllocateOrderResult.builder()
                        .pizzaOrderDto(request.getPizzaOrderDto())
                        .allocationError(false)
                        .pendingInventory(false)
                        .build());
    }
}
