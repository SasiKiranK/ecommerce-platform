package com.product.chain;

import com.product.model.Product;

public class PriceValidator extends ProductValidator {
    @Override
    protected void doValidate(Product product) {
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("❌ Product price must be > 0");
        }
    }
}
