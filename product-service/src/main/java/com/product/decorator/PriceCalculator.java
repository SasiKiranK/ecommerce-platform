package com.product.decorator;

import com.product.model.Product;

public interface PriceCalculator {
    double calculate(Product product);
}
