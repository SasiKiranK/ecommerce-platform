package com.product.factory;

import com.product.model.SimpleProduct;
import com.product.model.Product;

// Concrete Factory for SimpleProduct
public class SimpleProductFactory implements ProductFactoryInterface {

    private String name;
    private String description;
    private double price;
    private String category;

    // Constructor to pass parameters
    public SimpleProductFactory(String name, String description, double price, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    @Override
    public Product createProduct() {
        return new SimpleProduct(name, description, price, category);
    }
}
