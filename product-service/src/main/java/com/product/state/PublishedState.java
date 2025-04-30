package com.product.state;

public class PublishedState implements ProductState {

    @Override
    public void publish(ProductContext context) {
        System.out.println("❌ Already published.");
    }

    @Override
    public void archive(ProductContext context) {
        System.out.println("✔ Product moved from PUBLISHED to ARCHIVED");
        context.setState(new ArchivedState());
    }

    @Override
    public String getStateName() {
        return "PUBLISHED";
    }
}
