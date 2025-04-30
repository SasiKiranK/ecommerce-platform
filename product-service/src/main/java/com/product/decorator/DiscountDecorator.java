package com.product.decorator;

import com.product.model.Product;

public class DiscountDecorator extends PriceDecorator {
    public DiscountDecorator(PriceCalculator wrapped) {
        super(wrapped);
    }

    @Override
    public double calculate(Product product) {
        return wrapped.calculate(product) * 0.90;  // 10% discount
    }
}
