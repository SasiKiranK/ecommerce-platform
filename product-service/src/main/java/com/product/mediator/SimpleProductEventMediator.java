package com.product.mediator;

import com.product.model.Product;
import com.product.observer.NotificationService;
import com.product.singleton.LoggerService;

public class SimpleProductEventMediator implements ProductEventMediator {

    private final NotificationService notificationService;
    private final LoggerService loggerService;

    public SimpleProductEventMediator(NotificationService notificationService, LoggerService loggerService) {
        this.notificationService = notificationService;
        this.loggerService = loggerService;
    }

    @Override
    public void onProductCreated(Product product) {
        loggerService.log("Mediator: Product Created: " + product.getName());
        notificationService.sendProductCreatedNotification(product);
    }

    @Override
    public void onProductDeleted(Product product) {
        loggerService.log("Mediator: Product Deleted: " + product.getName());
        notificationService.sendProductDeletedNotification(product);
    }
}
