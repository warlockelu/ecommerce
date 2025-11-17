package com.example.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.model.Order;

@Service
public class PaymentService {
    private final OrderRepository orderRepo;

    public PaymentService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @KafkaListener(topics = "orders", groupId = "ecommerce-group")
    public void consumeOrderEvent(String orderId) {
        orderRepo.findById(orderId).ifPresent(order -> {
            order.setStatus("PAID");
            orderRepo.save(order);
            System.out.println("PaymentService: Order " + orderId + " payment processed.");
        });
    }
}
