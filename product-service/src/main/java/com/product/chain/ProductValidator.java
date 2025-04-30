package com.product.chain;

import com.product.model.Product;

public abstract class ProductValidator {

    protected ProductValidator next;

    public ProductValidator setNext(ProductValidator next) {
        this.next = next;
        return next;
    }

    public void validate(Product product) {
        doValidate(product);
        if (next != null) {
            next.validate(product); // pass to next
        }
    }

    protected abstract void doValidate(Product product);
}
