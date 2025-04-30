package com.product.chain;

import com.product.model.Product;

public class NameValidator extends ProductValidator {
    @Override
    protected void doValidate(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("❌ Product name is empty");
        }
    }
}
