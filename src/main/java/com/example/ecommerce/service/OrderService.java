package com.example.ecommerce.service;

import com.example.ecommerce.model.Item;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.repository.ItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final ItemRepository itemRepo;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderService(
            OrderRepository orderRepo,
            ItemRepository itemRepo,
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Order placeOrder(String username, List<String> itemIds) {
        List<Item> items = itemRepo.findAllById(itemIds);
        double total = items.stream().mapToDouble(Item::getPrice).sum();

        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, username, itemIds, total, "CREATED");
        orderRepo.save(order);

        kafkaTemplate.send("orders", orderId);

        return order;
    }

    public Order updateOrder(String orderId, String username, List<String> newItemIds) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUsername().equals(username)) {
            throw new RuntimeException("You are not allowed to update this order");
        }

        if ("CANCELLED".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Order already cancelled, cannot update");
        }

        List<Item> items = itemRepo.findAllById(newItemIds);
        double total = items.stream().mapToDouble(Item::getPrice).sum();

        order.setItemIds(newItemIds);
        order.setTotal(total);
        orderRepo.save(order);

        kafkaTemplate.send("orders", orderId);

        return order;
    }

    public void cancelOrder(String orderId, String username) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUsername().equals(username)) {
            throw new RuntimeException("You are not allowed to cancel this order");
        }

        order.setStatus("CANCELLED");
        orderRepo.save(order);

    }

    public Order getOrder(String orderId, String username) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUsername().equals(username)) {
            throw new RuntimeException("You are not allowed to view this order");
        }

        return order;
    }
}
