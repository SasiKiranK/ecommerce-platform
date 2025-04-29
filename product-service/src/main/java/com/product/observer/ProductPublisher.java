package com.product.observer;

import com.product.model.Product;

import java.util.ArrayList;
import java.util.List;

// Publisher Class
public class ProductPublisher {

    private List<ProductObserver> observers = new ArrayList<>();

    public void registerObserver(ProductObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(ProductObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Product product) {
        for (ProductObserver observer : observers) {
            observer.onProductCreated(product);
        }
    }
}
