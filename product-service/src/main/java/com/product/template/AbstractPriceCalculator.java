package com.product.template;

import com.product.model.Product;

public abstract class AbstractPriceCalculator {

    // Template method
    public final double calculateFinalPrice(Product product) {
        double base = product.getPrice();
        double discount = applyDiscount(product);
        double afterDiscount = base - discount;
        double taxed = applyTax(afterDiscount);
        return taxed;
    }

    // Hook method to be implemented
    protected abstract double applyDiscount(Product product);

    // Common tax method
    protected double applyTax(double amount) {
        return amount * 1.08; // 8% tax
    }
}
