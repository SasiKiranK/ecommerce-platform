package com.product.flyweight;

public class Product {
    private final String name;
    private final double price;
    private final ProductMetadata metadata; // shared

    public Product(String name, double price, ProductMetadata metadata) {
        this.name = name;
        this.price = price;
        this.metadata = metadata;
    }

    public String getDetails() {
        return "🛒 " + name + " ₹" + price +
               " | Brand: " + metadata.getBrand() +
               " | Category: " + metadata.getCategory();
    }
}
