package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService service;
    private StringBuilder logBuffer;
    private Logger logger;

    @BeforeEach
    void setup() {
        service = new OrderService();
        logger = Logger.getLogger(OrderService.class.getName());
        logBuffer = new StringBuilder();

        // Remove handlers antigos para evitar duplicação
        for (Handler h : logger.getHandlers()) {
            logger.removeHandler(h);
        }

        // Adiciona handler customizado para capturar os logs
        Handler testHandler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                logBuffer.append(record.getMessage()).append("\n");
            }

            @Override public void flush() {}
            @Override public void close() throws SecurityException {}
        };
        logger.addHandler(testHandler);
    }

    @Test
    void testAddAndCount() {
        service.addOrder(1, "Laptop");
        assertEquals(1, service.countOrders());
    }

    @Test
    void testGetOrder() {
        service.addOrder(2, "Phone");
        assertEquals("Phone", service.getOrder(2));
    }

    @Test
    void testRemoveOrder() {
        service.addOrder(1, "X");
        service.removeOrder(1);
        assertEquals(0, service.countOrders());
    }

    @Test
    void testAddDuplicateThrows() {
        service.addOrder(1, "A");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.addOrder(1, "B"));
        assertTrue(ex.getMessage().contains("Order already exists"));
    }

    @Test
    void testRiskyConcatSQLOutput() {
        String q = service.riskyConcatSQL("Robert'); DROP TABLE orders;--");
        assertTrue(q.contains("SELECT * FROM orders WHERE name = '"));
    }

    @Test
    void testDuplicateLogicSmallAndBig() {
        service.duplicateLogic(5);   // deve logar "Small number"
        service.duplicateLogic(50);  // deve logar "Big number"

        String logs = logBuffer.toString();
        assertTrue(logs.contains("Small number"), "Deveria logar 'Small number'");
        assertTrue(logs.contains("Big number"), "Deveria logar 'Big number'");
    }

    @Test
    void testDuplicateLogic2SmallAndBig() {
        service.duplicateLogic2(3);   // deve logar "Small number"
        service.duplicateLogic2(30);  // deve logar "Big number"

        String logs = logBuffer.toString();
        assertTrue(logs.contains("Small number"), "Deveria logar 'Small number'");
        assertTrue(logs.contains("Big number"), "Deveria logar 'Big number'");
    }
}
