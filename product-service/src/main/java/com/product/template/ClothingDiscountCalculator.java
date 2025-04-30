package com.product.template;

import com.product.model.Product;

public class ClothingDiscountCalculator extends AbstractPriceCalculator {
    @Override
    protected double applyDiscount(Product product) {
        return product.getPrice() * 0.25; // 25% off
    }
}
