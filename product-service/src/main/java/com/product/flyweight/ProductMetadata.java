package com.product.flyweight;

public class ProductMetadata {
    private final String brand;
    private final String category;

    public ProductMetadata(String brand, String category) {
        this.brand = brand;
        this.category = category;
    }

    public String getBrand() { return brand; }
    public String getCategory() { return category; }
}
