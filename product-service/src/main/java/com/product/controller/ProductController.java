package com.product.controller;

import com.product.builder.ProductBuilder;
import com.product.model.Product;
import com.product.model.SimpleProduct;
import com.product.abstractfactory.ConcreteProductFactory;
import com.product.abstractfactory.ProductAbstractFactory;
import com.product.observer.ProductCreationListener;
import com.product.observer.ProductPublisher;
import com.product.singleton.LoggerSingleton;
import com.product.service.ProductService;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductPublisher publisher = new ProductPublisher();
    private final LoggerSingleton logger = LoggerSingleton.getInstance();
    private final ProductAbstractFactory productFactory = new ConcreteProductFactory();
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;

        publisher.registerObserver(new ProductCreationListener("InventoryService"));
        publisher.registerObserver(new ProductCreationListener("NotificationService"));
    }

    // Create a simple product
    @PostMapping("/simple")
    public Product createSimpleProduct(@RequestParam String name,
                                       @RequestParam String description,
                                       @RequestParam double price,
                                       @RequestParam String category) {

        ProductBuilder builder = new ProductBuilder();
        SimpleProduct simpleProduct = builder
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setCategory(category)
                .buildSimpleProduct();

        logger.log("Creating Simple Product: " + name);

        publisher.notifyObservers(simpleProduct);
        return productService.saveProduct(simpleProduct);
    }

    // Create a bundle product
    @PostMapping("/bundle")
    public Product createBundleProduct(@RequestParam String bundleName,
                                       @RequestParam String description,
                                       @RequestParam double price) {

        logger.log("Creating Bundle Product: " + bundleName);

        List<Product> productsToBundle = productService.getAllProducts();

        Product bundle = productFactory.createBundleProduct(bundleName, description, price, productsToBundle);

        publisher.notifyObservers(bundle);
        return productService.saveProduct(bundle);
    }

    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        logger.log("Fetching all products...");
        return productService.getAllProducts();
    }
}
