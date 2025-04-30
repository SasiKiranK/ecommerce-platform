package com.product.command;

import com.product.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Product> items = new ArrayList<>();

    public void add(Product product) {
        items.add(product);
        System.out.println("✅ Added to cart: " + product.getName());
    }

    public void remove(Product product) {
        items.remove(product);
        System.out.println("❌ Removed from cart: " + product.getName());
    }

    public List<Product> getItems() {
        return items;
    }
}
