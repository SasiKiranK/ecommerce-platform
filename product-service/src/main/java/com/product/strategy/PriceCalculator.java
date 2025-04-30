package com.product.strategy;

public class PriceCalculator {

    private PricingStrategy strategy;

    public PriceCalculator(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculate(double basePrice) {
        return strategy.calculatePrice(basePrice);
    }

    // allow changing strategy dynamically
    public void setStrategy(PricingStrategy strategy) {
        this.strategy = strategy;
    }
}
