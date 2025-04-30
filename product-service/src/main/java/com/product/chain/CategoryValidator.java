package com.product.chain;

import com.product.model.Product;

import java.util.Set;

public class CategoryValidator extends ProductValidator {

    private static final Set<String> validCategories = Set.of("electronics", "clothing", "grocery");

    @Override
    protected void doValidate(Product product) {
        if (!validCategories.contains(product.getCategory().toLowerCase())) {
            throw new IllegalArgumentException("❌ Invalid category: " + product.getCategory());
        }
    }
}
