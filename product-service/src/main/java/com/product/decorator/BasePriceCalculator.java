package com.product.decorator;

import com.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class BasePriceCalculator implements PriceCalculator {
    public double calculate(Product product) {
        return product.getPrice();  // base price only
    }
}
