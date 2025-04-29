package com.product.factory;

import com.product.model.BundleProduct;
import com.product.model.Product;
import java.util.List;

// Concrete Factory for BundleProduct
public class BundleProductFactory implements ProductFactoryInterface {

    private String name;
    private String description;
    private double price;
    private List<Product> products;

    public BundleProductFactory(String name, String description, double price, List<Product> products) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.products = products;
    }

    @Override
    public Product createProduct() {
        BundleProduct bundle = new BundleProduct(name, description, price);
        for (Product product : products) {
            bundle.addProduct(product);
        }
        return bundle;
    }
}
