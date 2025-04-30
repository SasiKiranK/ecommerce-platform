package com.product.decorator;

import com.product.model.Product;

public abstract class PriceDecorator implements PriceCalculator {
    protected final PriceCalculator wrapped;

    public PriceDecorator(PriceCalculator wrapped) {
        this.wrapped = wrapped;
    }

    public abstract double calculate(Product product);
}
