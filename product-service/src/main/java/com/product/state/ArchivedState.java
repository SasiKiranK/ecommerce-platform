package com.product.state;

public class ArchivedState implements ProductState {

    @Override
    public void publish(ProductContext context) {
        System.out.println("❌ Cannot publish archived product.");
    }

    @Override
    public void archive(ProductContext context) {
        System.out.println("❌ Already archived.");
    }

    @Override
    public String getStateName() {
        return "ARCHIVED";
    }
}
