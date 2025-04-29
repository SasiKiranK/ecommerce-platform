package com.product.model;

import com.product.prototype.ProductPrototype;


public class SimpleProduct extends Product implements ProductPrototype {

    private String category;
    
    public SimpleProduct(){
        super();
    }

    public SimpleProduct(String name, String description, double price, String category) {
        super(name, description, price);
        this.category = category;
    }

    @Override
    public void displayInfo() {
        System.out.println("Simple Product: " + name);
        System.out.println("- ID: " + id);
        System.out.println("- Description: " + description);
        System.out.println("- Price: $" + price);
        System.out.println("- Category: " + category);
    }

      // Getters & Setters
      public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    @Override
    public Product cloneProduct() {
        return new SimpleProduct(this.name, this.description, this.price, this.category);
    }

}