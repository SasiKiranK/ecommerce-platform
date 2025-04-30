package com.product.memento;

import java.util.Stack;

public class ProductHistory {
    private final Stack<ProductMemento> history = new Stack<>();

    public void save(ProductMemento state) {
        history.push(state);
    }

    public ProductMemento undo() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        throw new IllegalStateException("No previous state to undo.");
    }
}
