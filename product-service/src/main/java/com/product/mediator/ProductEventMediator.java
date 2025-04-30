package com.product.mediator;

import com.product.model.Product;

public interface ProductEventMediator {
    void onProductCreated(Product product);
    void onProductDeleted(Product product);
}
