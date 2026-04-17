package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {
    private final Map<String, Order> orders = new ConcurrentHashMap<>();

    public Order save(Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    public Order findById(String id) {
        return orders.get(id);
    }

    public void updateStatus(String id, String status) {
        Order order = orders.get(id);
        if (order != null) {
            order.setStatus(status);
        }
    }
}