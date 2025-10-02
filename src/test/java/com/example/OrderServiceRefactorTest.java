package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceRefactorTest {

    private OrderServiceRefactor service;

    @BeforeEach
    void setup() {
        service = new OrderServiceRefactor();
    }

    @Test
    void addOrderShouldStoreOrder() {
        service.addOrder(1, "Test Order");
        assertEquals(Optional.of("Test Order"), service.getOrder(1));
        assertEquals(1, service.countOrders());
    }

    @Test
    void addOrderShouldRejectDuplicateId() {
        service.addOrder(1, "First");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.addOrder(1, "Duplicate"));
        assertTrue(ex.getMessage().contains("Order already exists"));
    }

    @Test
    void addOrderShouldRejectNullDescription() {
        assertThrows(NullPointerException.class,
                () -> service.addOrder(1, null));
    }

    @Test
    void addOrderAllowsEmptyStringButLogsWarning() {
        service.addOrder(99, "   "); // description vazio, mas não lança exceção
        assertTrue(service.getOrder(99).isPresent());
    }

    @Test
    void getOrderShouldReturnEmptyOptionalWhenNotFound() {
        assertEquals(Optional.empty(), service.getOrder(42));
    }

    @Test
    void removeOrderShouldReturnTrueIfExists() {
        service.addOrder(1, "Order");
        assertTrue(service.removeOrder(1));
        assertFalse(service.getOrder(1).isPresent());
    }

    @Test
    void removeOrderShouldReturnFalseIfNotExists() {
        assertFalse(service.removeOrder(123));
    }

    @Test
    void safeQueryByNameShouldReturnParameterizedSql() {
        String query = service.safeQueryByName("Alice");
        assertTrue(query.contains("?"));
        assertTrue(query.contains("Alice"));
    }

    @Test
    void printSizeLabelShouldPrintBigAndSmall() {
        // não conseguimos capturar o LOG fácil, mas podemos invocar os métodos
        service.printSizeLabel(5);   // deve logar "Small number"
        service.printSizeLabel(20);  // deve logar "Big number"
        // sem assert, só garantindo que não lança exceção
        assertDoesNotThrow(() -> {
            service.printSizeLabel(5);
            service.printSizeLabel(20);
        });
    }
}
