package com.product.facade;

import com.product.model.Product;

public class ProductValidator {
    public void validate(Product product) {
        if (product.getName().isBlank()) throw new IllegalArgumentException("❌ Name is empty");
        if (product.getPrice() <= 0) throw new IllegalArgumentException("❌ Price is invalid");
    }
}
