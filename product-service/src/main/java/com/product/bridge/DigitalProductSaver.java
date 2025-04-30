package com.product.bridge;

import com.product.model.Product;

public class DigitalProductSaver extends ProductSaver {
    public DigitalProductSaver(ProductPersistence persistence) {
        super(persistence);
    }

    @Override
    public void saveProduct(Product product) {
        System.out.println("🌐 Saving digital product...");
        persistence.save(product);
    }
}
