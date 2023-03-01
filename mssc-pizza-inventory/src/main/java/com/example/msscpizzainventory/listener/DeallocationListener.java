package com.example.msscpizzainventory.listener;

import com.example.model.event.DeallocateOrderRequest;
import com.example.msscpizzainventory.config.JmsConfig;
import com.example.msscpizzainventory.service.AllocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeallocationListener {

    private final AllocationService allocationService;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listen(DeallocateOrderRequest request){
        allocationService.deallocateOrder(request.getPizzaOrderDto());
    }
}
