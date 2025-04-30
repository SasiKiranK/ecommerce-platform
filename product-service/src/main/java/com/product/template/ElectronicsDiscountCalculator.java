package com.product.template;

import com.product.model.Product;

public class ElectronicsDiscountCalculator extends AbstractPriceCalculator {
    @Override
    protected double applyDiscount(Product product) {
        return product.getPrice() * 0.10; // 10% off
    }
}
