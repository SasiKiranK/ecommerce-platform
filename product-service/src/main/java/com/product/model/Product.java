package com.product.model;

import java.util.UUID;

// Abstract Base class
@Document(collection = "products") // 👈 MongoDB collection name
public abstract class Product {
    
    @Id
    protected String id;
    protected String name;
    protected String description;
    protected double price;

    // auto assign random id
    public Product() {
        this.id = UUID.randomUUID().toString();
    }

    public Product(String name, String description, double price) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public abstract void displayInfo();

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
