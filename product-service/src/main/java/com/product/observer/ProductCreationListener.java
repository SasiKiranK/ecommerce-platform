package com.product.observer;

import com.product.model.Product;

// A simple Observer
public class ProductCreationListener implements ProductObserver {

    private String listenerName;

    public ProductCreationListener(String listenerName) {
        this.listenerName = listenerName;
    }

    @Override
    public void onProductCreated(Product product) {
        System.out.println("[" + listenerName + "] New Product Created: " + product.getName() + " (ID: " + product.getId() + ")");
    }
}
