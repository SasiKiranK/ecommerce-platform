package com.product.bridge;

import com.product.model.Product;

public abstract class ProductSaver {
    protected ProductPersistence persistence;

    public ProductSaver(ProductPersistence persistence) {
        this.persistence = persistence;
    }

    public abstract void saveProduct(Product product);
}
