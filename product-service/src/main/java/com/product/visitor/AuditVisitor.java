package com.product.visitor;

import com.product.model.Product;

public class AuditVisitor implements ProductVisitor {
    @Override
    public void visit(Product product) {
        System.out.println("📢 AUDIT: Accessed product → " + product.getName() + ", price: " + product.getPrice());
    }
}
