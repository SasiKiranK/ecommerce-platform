package com.product.decorator;

import com.product.model.Product;

public class GstTaxDecorator extends PriceDecorator {
    public GstTaxDecorator(PriceCalculator wrapped) {
        super(wrapped);
    }

    @Override
    public double calculate(Product product) {
        double base = wrapped.calculate(product);
        double gst = base * 0.18;
        return base + gst;
    }
}
