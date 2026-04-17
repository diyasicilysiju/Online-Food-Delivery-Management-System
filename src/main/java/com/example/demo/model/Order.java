package com.example.demo.model;

public class Order {
    private String id;
    private String item;
    private String status; // PLACED, PREPARING, DELIVERED
    private long timestamp;

    // Constructors, getters, setters
    public Order() {}
    public Order(String id, String item) {
        this.id = id;
        this.item = item;
        this.status = "PLACED";
        this.timestamp = System.currentTimeMillis();
    }
    // (add all getters/setters)
}