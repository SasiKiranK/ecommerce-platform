package com.product.command;

import com.product.model.Product;

public class AddToCartCommand implements CartCommand {

    private final Cart cart;
    private final Product product;

    public AddToCartCommand(Cart cart, Product product) {
        this.cart = cart;
        this.product = product;
    }

    @Override
    public void execute() {
        cart.add(product);
    }

    @Override
    public void undo() {
        cart.remove(product);
    }
}