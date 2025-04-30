package com.product.strategy;

public class PremiumPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 1.2; // 20% premium
    }
}
