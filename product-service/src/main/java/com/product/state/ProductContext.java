package com.product.state;

public class ProductContext {

    private ProductState currentState;

    public ProductContext() {
        this.currentState = new DraftState(); // default state
    }

    public void setState(ProductState state) {
        this.currentState = state;
    }

    public String getState() {
        return currentState.getStateName();
    }

    public void publish() {
        currentState.publish(this);
    }

    public void archive() {
        currentState.archive(this);
    }
}
