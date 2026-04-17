package com.example.demo;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {
    private OrderService service;
    private OrderRepository repository;

    @BeforeEach
    void setUp() {
        repository = new OrderRepository();
        service = new OrderService(repository);
    }

    @Test
    void placeOrder_Success() {
        Order order = service.placeOrder("Pizza");
        assertNotNull(order.getId());
        assertEquals("Pizza", order.getItem());
        assertEquals("PLACED", order.getStatus());
    }

    @Test
    void placeOrder_EmptyItem_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.placeOrder(""));
        assertThrows(IllegalArgumentException.class, () -> service.placeOrder(null));
    }

    @Test
    void getOrder_NotFound_ThrowsException() {
        assertThrows(RuntimeException.class, () -> service.getOrder("invalid-id"));
    }

    @Test
    void getOrder_Success() {
        Order placed = service.placeOrder("Burger");
        Order found = service.getOrder(placed.getId());
        assertEquals(placed.getId(), found.getId());
    }

    @Test
    void updateOrderStatus_Success() {
        Order placed = service.placeOrder("Fries");
        service.updateOrderStatus(placed.getId(), "PREPARING");
        Order updated = service.getOrder(placed.getId());
        assertEquals("PREPARING", updated.getStatus());
    }
}