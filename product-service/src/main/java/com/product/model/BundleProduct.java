package com.product.model;

import com.product.prototype.ProductPrototype;

import java.util.ArrayList;
import java.util.List;

public class BundleProduct extends Product implements ProductPrototype {

    private List<Product> products = new ArrayList<>();

    public BundleProduct() {
        super();
    }

    public BundleProduct(String name, String description, double price) {
        super(name, description, price);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void displayInfo() {
        displayInfo(0);
    }

    private void displayInfo(int indentLevel) {
        String indent = "  ".repeat(indentLevel);

        System.out.println(indent + "Bundle Product: " + name);
        System.out.println(indent + "- ID: " + id);
        System.out.println(indent + "- Description: " + description);
        System.out.println(indent + "- Price: $" + price);
        System.out.println(indent + "- Includes:");

        for (Product product : products) {
            if (product instanceof BundleProduct) {
                ((BundleProduct) product).displayInfo(indentLevel + 1);
            } else {
                System.out.println(indent + "  Simple Product: " + product.getName());
                System.out.println(indent + "  - ID: " + product.getId());
                System.out.println(indent + "  - Description: " + product.getDescription());
                System.out.println(indent + "  - Price: $" + product.getPrice());
            }
            System.out.println();
        }
    }

    @Override
    public Product cloneProduct() {
        BundleProduct clonedBundle = new BundleProduct(this.name, this.description, this.price);
        for (Product product : products) {
            if (product instanceof ProductPrototype) {
                clonedBundle.addProduct(((ProductPrototype) product).cloneProduct());
            }
        }
        return clonedBundle;
    }
}
