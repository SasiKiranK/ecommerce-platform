package com.product.state;

public interface ProductState {
    void publish(ProductContext context);
    void archive(ProductContext context);
    String getStateName();
}
