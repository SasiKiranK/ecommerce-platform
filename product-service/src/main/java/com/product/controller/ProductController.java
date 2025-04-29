package com.product.controller;

import com.product.builder.ProductBuilder;
import com.product.dto.ProductRequestDTO;
import com.product.dto.ProductResponseDTO;
import com.product.model.Product;
import com.product.model.SimpleProduct;
import com.product.abstractfactory.ConcreteProductFactory;
import com.product.abstractfactory.ProductAbstractFactory;
import com.product.observer.ProductCreationListener;
import com.product.observer.ProductPublisher;
import com.product.singleton.LoggerSingleton;
import com.product.service.ProductService;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

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
    public ProductResponseDTO createSimpleProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {

        ProductBuilder builder = new ProductBuilder();
        SimpleProduct simpleProduct = builder
                .setName(requestDTO.getName())
                .setDescription(requestDTO.getDescription())
                .setPrice(requestDTO.getPrice())
                .setCategory(requestDTO.getCategory())
                .buildSimpleProduct();

        logger.log("Creating Simple Product: " + requestDTO.getName());

        publisher.notifyObservers(simpleProduct);
        Product saved = productService.saveProduct(simpleProduct);

        return new ProductResponseDTO(
            saved.getId(),
            saved.getName(),
            saved.getDescription(),
            saved.getPrice(),
            requestDTO.getCategory()
        );
    }

    // Get all products
    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        logger.log("Fetching all products...");

        return productService.getAllProducts()
                .stream()
                .map(p -> new ProductResponseDTO(
                    p.getId(),
                    p.getName(),
                    p.getDescription(),
                    p.getPrice(),
                    (p instanceof SimpleProduct) ? ((SimpleProduct) p).getCategory() : "Bundle"
                ))
                .collect(Collectors.toList());
    }

    // Update an existing product
@PutMapping("/{id}")
public ProductResponseDTO updateProduct(@PathVariable String id,
                                        @Valid @RequestBody ProductRequestDTO requestDTO) {

    ProductBuilder builder = new ProductBuilder();
    SimpleProduct updatedProduct = builder
            .setName(requestDTO.getName())
            .setDescription(requestDTO.getDescription())
            .setPrice(requestDTO.getPrice())
            .setCategory(requestDTO.getCategory())
            .buildSimpleProduct();

    Product saved = productService.updateProduct(id, updatedProduct);

    return new ProductResponseDTO(
        saved.getId(),
        saved.getName(),
        saved.getDescription(),
        saved.getPrice(),
        requestDTO.getCategory()
    );
}

// Delete a product
@DeleteMapping("/{id}")
public String deleteProduct(@PathVariable String id) {
    productService.deleteProduct(id);
    return "Product with ID " + id + " has been deleted successfully.";
}

}
