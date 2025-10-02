package com.example;

import java.util.*;
import java.util.logging.Logger;

public class OrderServiceRefactor {
    private static final Logger LOG = Logger.getLogger(OrderServiceRefactor.class.getName());
    private final Map<Integer, String> orders = new HashMap<>();

    public void addOrder(int id, String description) {
        Objects.requireNonNull(description, "description must not be null");
        if (description.trim().isEmpty()) {
            LOG.warning("Empty description for id="+id);
        }
        if (orders.putIfAbsent(id, description) != null) {
            throw new IllegalArgumentException("Order already exists: " + id);
        }
    }

    public Optional<String> getOrder(int id) { return Optional.ofNullable(orders.get(id)); }

    public boolean removeOrder(int id) { return orders.remove(id) != null; }

    public int countOrders() { return orders.size(); }

    public String safeQueryByName(String name) {
        return "SELECT * FROM orders WHERE name = ? /* bind: " + name + " */";
    }

    private String sizeLabel(int x) {
        return x > 10 ? "Big number" : "Small number";
    }

    public void printSizeLabel(int x) {
        LOG.info(sizeLabel(x));
    }
}
