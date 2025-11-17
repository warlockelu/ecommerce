package com.example.ecommerce.controller;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.OrderService;
import java.security.Principal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody List<String> itemIds, Principal principal) {
        String username = principal.getName();
        Order order = orderService.placeOrder(username, itemIds);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable("id") String orderId,
            @RequestBody List<String> itemIds,
            Principal principal
    ) {
        String username = principal.getName();
        Order updated = orderService.updateOrder(orderId, username, itemIds);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable("id") String orderId,
            Principal principal
    ) {
        String username = principal.getName();
        orderService.cancelOrder(orderId, username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(
            @PathVariable("id") String orderId,
            Principal principal
    ) {
        String username = principal.getName();
        Order order = orderService.getOrder(orderId, username);
        return ResponseEntity.ok(order);
    }
}
