package com.product.command;

import com.product.model.Product;

public class RemoveFromCartCommand implements CartCommand {

    private final Cart cart;
    private final Product product;

    public RemoveFromCartCommand(Cart cart, Product product) {
        this.cart = cart;
        this.product = product;
    }

    @Override
    public void execute() {
        cart.remove(product);
    }

    @Override
    public void undo() {
        cart.add(product);
    }
}
