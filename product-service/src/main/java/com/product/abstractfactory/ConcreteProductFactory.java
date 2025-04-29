package com.product.abstractfactory;

import com.product.model.Product;
import com.product.model.SimpleProduct;
import com.product.model.BundleProduct;

import java.util.List;

public class ConcreteProductFactory implements ProductAbstractFactory {

    @Override
    public Product createSimpleProduct(String name, String description, double price, String category) {
        return new SimpleProduct(name, description, price, category);
    }

    @Override
    public Product createBundleProduct(String name, String description, double price, List<Product> products) {
        BundleProduct bundle = new BundleProduct(name, description, price);
        for (Product product : products) {
            bundle.addProduct(product);
        }
        return bundle;
    }
}
