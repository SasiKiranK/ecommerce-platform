package com.product.facade;

import com.product.model.Product;

public class ProductRepository {
    public void save(Product product) {
        System.out.println("🗃️ Product saved: " + product.getName());
    }
}
