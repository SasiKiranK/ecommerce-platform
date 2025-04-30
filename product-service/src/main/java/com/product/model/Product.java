package com.product.model;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.product.visitor.ProductVisitor;
import com.product.memento.ProductMemento;


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

      // Accept a visitor
      public void accept(ProductVisitor visitor) {
        visitor.visit(this);
    }


        // Memento methods
        public ProductMemento save() {
            return new ProductMemento(name, description, price, category);
        }
    
        public void restore(ProductMemento memento) {
            this.name = memento.getName();
            this.description = memento.getDescription();
            this.price = memento.getPrice();
            this.category = memento.getCategory();
        }



}
