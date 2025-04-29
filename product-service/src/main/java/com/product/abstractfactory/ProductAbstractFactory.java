package com.product.abstractfactory;

import com.product.model.Product;

import java.util.List;

public interface ProductAbstractFactory {
    Product createSimpleProduct(String name, String description, double price, String category);
    Product createBundleProduct(String name, String description, double price, List<Product> products);
}
