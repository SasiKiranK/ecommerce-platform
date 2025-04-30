package com.product.bridge;

import com.product.model.Product;

public class MongoProductPersistence implements ProductPersistence {
    @Override
    public void save(Product product) {
        System.out.println("📦 Saving product to MongoDB: " + product.getName());
    }
}
