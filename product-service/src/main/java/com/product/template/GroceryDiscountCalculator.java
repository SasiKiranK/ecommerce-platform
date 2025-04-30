package com.product.template;

import com.product.model.Product;

public class GroceryDiscountCalculator extends AbstractPriceCalculator {
    @Override
    protected double applyDiscount(Product product) {
        return 0.0; // No discount
    }
}
