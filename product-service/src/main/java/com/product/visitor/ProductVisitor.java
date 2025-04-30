package com.product.visitor;

import com.product.model.Product;

public interface ProductVisitor {
    void visit(Product product);
}
