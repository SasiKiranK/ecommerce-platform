package com.product.command;

import java.util.Stack;

public class CartService {

    private final Stack<CartCommand> history = new Stack<>();

    public void executeCommand(CartCommand command) {
        command.execute();
        history.push(command);
    }

    public void undoLast() {
        if (!history.isEmpty()) {
            CartCommand last = history.pop();
            last.undo();
        } else {
            System.out.println("No command to undo.");
        }
    }
}
