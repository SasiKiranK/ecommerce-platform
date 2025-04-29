package com.product.dto;

public class ProductResponseDTO {

    private String id;
    private String name;
    private String description;
    private double price;
    private String category;

    // Constructor
    public ProductResponseDTO(String id, String name, String description, double price, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    // Getters only (ResponseDTOs usually no setters)
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
