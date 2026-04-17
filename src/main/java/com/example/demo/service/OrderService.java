package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order placeOrder(String item) {
        if (item == null || item.trim().isEmpty()) {
            throw new IllegalArgumentException("Item cannot be empty");
        }
        String id = UUID.randomUUID().toString();
        Order order = new Order(id, item);
        return repository.save(order);
    }

    public Order getOrder(String id) {
        Order order = repository.findById(id);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        return order;
    }

    public void updateOrderStatus(String id, String status) {
        repository.updateStatus(id, status);
    }
}