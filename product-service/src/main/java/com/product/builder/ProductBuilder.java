package com.product.builder;

import com.product.model.SimpleProduct;

public class ProductBuilder {

    private String name;
    private String description;
    private double price;
    private String category;

    // Optional future fields: discount, images, tags, etc.

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    // Finally build the product
    public SimpleProduct buildSimpleProduct() {
        return new SimpleProduct(name, description, price, category);
    }
}
