package com.product.observer;

import com.product.model.Product;

// Observer Interface
public interface ProductObserver {
    void onProductCreated(Product product);
}
