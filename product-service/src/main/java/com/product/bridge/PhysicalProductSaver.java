package com.product.bridge;

import com.product.model.Product;

public class PhysicalProductSaver extends ProductSaver {
    public PhysicalProductSaver(ProductPersistence persistence) {
        super(persistence);
    }

    @Override
    public void saveProduct(Product product) {
        System.out.println("📦 Saving physical product...");
        persistence.save(product);
    }
}
