package com.product.visitor;

import com.product.model.Product;

public class TaxCalculatorVisitor implements ProductVisitor {
    @Override
    public void visit(Product product) {
        double tax = product.getPrice() * 0.08;
        System.out.println("💰 Tax for product " + product.getName() + " is ₹" + tax);
    }
}
