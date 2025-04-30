package com.product.memento;

public class ProductMemento {
    private final String name;
    private final String description;
    private final double price;
    private final String category;

    public ProductMemento(String name, String description, double price, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
}
