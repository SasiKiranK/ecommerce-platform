package com.product.bridge;

import com.product.model.Product;

public class PostgresProductPersistence implements ProductPersistence {
    @Override
    public void save(Product product) {
        System.out.println("🐘 Saving product to PostgreSQL: " + product.getName());
    }
}
