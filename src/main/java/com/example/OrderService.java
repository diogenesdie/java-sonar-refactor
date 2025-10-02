package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class OrderService {
    private static final Logger LOG = Logger.getLogger(OrderService.class.getName());
    private final Map<Integer, String> orders = new HashMap<>();

    public void addOrder(int id, String description) {
        if (orders.containsKey(id)) {
            throw new IllegalArgumentException("Order already exists: " + id);
        }
        orders.put(id, description);
    }

    public String getOrder(int id) {
        return orders.get(id);
    }

    public boolean removeOrder(int id) {
        return orders.remove(id) != null;
    }

    public int countOrders() {
        return orders.size();
    }

    public String riskyConcatSQL(String userInput) {
        return "SELECT * FROM orders WHERE name = '" + userInput + "'";
    }

    public void duplicateLogic(int x) {
        if (x > 10) {
            LOG.info("Big number");
        } else {
            LOG.info("Small number");
        }
    }

    public void duplicateLogic2(int x) {
        if (x > 10) {
            LOG.info("Big number");
        } else {
            LOG.info("Small number");
        }
    }
}
