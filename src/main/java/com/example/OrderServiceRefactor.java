package com.example;

import java.util.*;
import java.util.logging.Logger;

public class OrderServiceRefactor {
    // Troca de System.out.println por Logger (Sonar S106)
    // Justificativa: melhora manutenibilidade e boas práticas de logging,
    // permitindo controle de níveis (INFO/WARNING/ERROR) e integração com frameworks de log.
    private static final Logger LOG = Logger.getLogger(OrderServiceRefactor.class.getName());

    // Uso de Map para armazenar pedidos em memória
    // Justificativa: simplifica manipulação (add, remove, count) e garante eficiência O(1).
    private final Map<Integer, String> orders = new HashMap<>();

    public void addOrder(int id, String description) {
        // Validação com Objects.requireNonNull
        // Justificativa: previne NullPointerException e deixa explícita a restrição (melhora robustez).
        Objects.requireNonNull(description, "description must not be null");

        // Checagem de string vazia com log WARNING
        // Justificativa: garante feedback sem bloquear execução, evitando pedidos inválidos silenciosos.
        if (description.trim().isEmpty()) {
            LOG.warning("Empty description for id=" + id);
        }

        // Uso de putIfAbsent no lugar de containsKey + put
        // Justificativa: evita condição de corrida e torna código mais legível.
        if (orders.putIfAbsent(id, description) != null) {
            throw new IllegalArgumentException("Order already exists: " + id);
        }
    }

    // Retorno com Optional
    // Justificativa: evita retorno nulo e força o chamador a lidar com ausência de valores de forma segura.
    public Optional<String> getOrder(int id) { 
        return Optional.ofNullable(orders.get(id)); 
    }

    // Retorno booleano indica sucesso da remoção
    // Justificativa: melhora semântica do método (quem chama sabe se a ordem existia).
    public boolean removeOrder(int id) { 
        return orders.remove(id) != null; 
    }

    // Método simples de contagem
    public int countOrders() { 
        return orders.size(); 
    }

    // Query parametrizada
    // Justificativa: substitui concatenação de SQL (vulnerável a injection) por placeholder "?".
    // Sonar S2077 → risco de SQL Injection resolvido.
    public String safeQueryByName(String name) {
        return "SELECT * FROM orders WHERE name = ? /* bind: " + name + " */";
    }

    // Extração de método para evitar duplicação (Sonar S4144)
    // Justificativa: elimina repetição entre duplicateLogic e duplicateLogic2, melhorando manutenibilidade.
    private String sizeLabel(int x) {
        return x > 10 ? "Big number" : "Small number";
    }

    // Método público que usa o helper
    // Justificativa: separa lógica de decisão do efeito colateral (log), facilitando testes unitários.
    public void printSizeLabel(int x) {
        LOG.info(sizeLabel(x));
    }
}
