package com.product.strategy;

public class DiscountPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 0.9; // 10% discount
    }
}
