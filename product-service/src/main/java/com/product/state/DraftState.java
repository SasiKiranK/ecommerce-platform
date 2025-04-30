package com.product.state;

public class DraftState implements ProductState {

    @Override
    public void publish(ProductContext context) {
        System.out.println("✔ Product moved from DRAFT to PUBLISHED");
        context.setState(new PublishedState());
    }

    @Override
    public void archive(ProductContext context) {
        System.out.println("❌ Cannot archive a draft. Publish first.");
    }

    @Override
    public String getStateName() {
        return "DRAFT";
    }
}
