package com.product.controller;

import com.product.builder.ProductBuilder;
import com.product.model.Product;
import com.product.model.SimpleProduct;
import com.product.abstractfactory.ConcreteProductFactory;
import com.product.abstractfactory.ProductAbstractFactory;
import com.product.observer.ProductCreationListener;
import com.product.observer.ProductPublisher;
import com.product.singleton.LoggerSingleton;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductPublisher publisher = new ProductPublisher();
    private final LoggerSingleton logger = LoggerSingleton.getInstance();
    private final ProductAbstractFactory productFactory = new ConcreteProductFactory();
    private final List<Product> productStore = new ArrayList<>(); // temporary in-memory store

    public ProductController() {
        // Register 2 listeners
        publisher.registerObserver(new ProductCreationListener("InventoryService"));
        publisher.registerObserver(new ProductCreationListener("NotificationService"));
    }

    // Create a simple product
    @PostMapping("/simple")
    public Product createSimpleProduct(@RequestParam String name,
                                       @RequestParam String description,
                                       @RequestParam double price,
                                       @RequestParam String category) {

        // Using Builder + Factory + Observer
        ProductBuilder builder = new ProductBuilder();
        SimpleProduct simpleProduct = builder
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setCategory(category)
                .buildSimpleProduct();

        logger.log("Creating Simple Product: " + name);

        publisher.notifyObservers(simpleProduct);
        productStore.add(simpleProduct);

        return simpleProduct;
    }

    // Create a bundle product
    @PostMapping("/bundle")
    public Product createBundleProduct(@RequestParam String bundleName,
                                       @RequestParam String description,
                                       @RequestParam double price) {

        logger.log("Creating Bundle Product: " + bundleName);

        // Select first 2 products to bundle (demo purpose)
        List<Product> productsToBundle = new ArrayList<>();
        if (productStore.size() >= 2) {
            productsToBundle.add(productStore.get(0));
            productsToBundle.add(productStore.get(1));
        }

        Product bundle = productFactory.createBundleProduct(bundleName, description, price, productsToBundle);

        publisher.notifyObservers(bundle);
        productStore.add(bundle);

        return bundle;
    }

    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        logger.log("Fetching all products...");
        return productStore;
    }
}
