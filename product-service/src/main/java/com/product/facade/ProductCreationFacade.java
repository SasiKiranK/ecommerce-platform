package com.product.facade;

import com.product.model.Product;

public class ProductCreationFacade {

    private final ProductValidator validator = new ProductValidator();
    private final MetadataService metadataService = new MetadataService();
    private final ProductRepository repository = new ProductRepository();
    private final LoggerService logger = new LoggerService();
    private final NotifierService notifier = new NotifierService();

    public void createProduct(Product product) {
        validator.validate(product);
        metadataService.saveBrand(product.getBrand());
        metadataService.saveCategory(product.getCategory());
        repository.save(product);
        logger.log("Product created: " + product.getName());
        notifier.send("Product created successfully.");
    }
}
