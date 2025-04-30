package com.product.decorator;

import com.product.model.Product;

public class ShippingDecorator extends PriceDecorator {
    public ShippingDecorator(PriceCalculator wrapped) {
        super(wrapped);
    }

    @Override
    public double calculate(Product product) {
        return wrapped.calculate(product) + 49;  // flat ₹49 shipping
    }
}
