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

@GetMapping("/price-strategy")
public String calculatePriceWithStrategy(@RequestParam double basePrice,
                                         @RequestParam String strategy) {

    PricingStrategy selectedStrategy;

    switch (strategy.toLowerCase()) {
        case "discount":
            selectedStrategy = new DiscountPricingStrategy();
            break;
        case "premium":
            selectedStrategy = new PremiumPricingStrategy();
            break;
        case "regular":
        default:
            selectedStrategy = new RegularPricingStrategy();
    }

    PriceCalculator calculator = new PriceCalculator(selectedStrategy);
    double finalPrice = calculator.calculate(basePrice);

    return "Final price using " + strategy + " strategy: " + finalPrice;
}

@GetMapping("/command-test")
public String testCommandPattern() {
    Cart cart = new Cart();
    CartService cartService = new CartService();

    Product product1 = new SimpleProduct("iPhone", "Apple iPhone", 999.0, "Mobile");
    Product product2 = new SimpleProduct("Shoes", "Nike Air", 150.0, "Footwear");

    cartService.executeCommand(new AddToCartCommand(cart, product1));
    cartService.executeCommand(new AddToCartCommand(cart, product2));
    cartService.executeCommand(new RemoveFromCartCommand(cart, product1));

    cartService.undoLast(); // Undo remove iPhone
    cartService.undoLast(); // Undo add Shoes

    return "Final cart size: " + cart.getItems().size();
}

@GetMapping("/mediator-demo")
public String mediatorTest() {
    LoggerService logger = LoggerService.getInstance();
    NotificationService notifier = new NotificationService();
    ProductEventMediator mediator = new SimpleProductEventMediator(notifier, logger);

    Product product = new SimpleProduct("Keyboard", "Mechanical", 129.0, "Electronics");

    mediator.onProductCreated(product);
    mediator.onProductDeleted(product);

    return "Mediator test complete";
}

@GetMapping("/state-demo")
public String testStatePattern() {
    ProductContext product = new ProductContext();

    System.out.println("Initial State: " + product.getState());
    product.publish();  // should move to Published
    System.out.println("State after publish: " + product.getState());
    product.archive();  // should move to Archived
    System.out.println("State after archive: " + product.getState());

    product.publish(); // not allowed from Archived

    return "Check console for state transitions.";
}

@GetMapping("/template-test")
public String templateDemo(@RequestParam String type) {
    Product p = new Product("T-Shirt", "100% cotton", 100.0);
    AbstractPriceCalculator calculator;

    switch (type.toLowerCase()) {
        case "electronics" -> calculator = new ElectronicsDiscountCalculator();
        case "clothing" -> calculator = new ClothingDiscountCalculator();
        case "grocery" -> calculator = new GroceryDiscountCalculator();
        default -> throw new IllegalArgumentException("Unknown type: " + type);
    }

    double finalPrice = calculator.calculateFinalPrice(p);
    return "Final price for " + type + " is ₹" + finalPrice;
}

@GetMapping("/validate-product")
public String validateChain() {
    Product p = new Product("Laptop", "High-end gaming", 1200.0, "electronics");

    ProductValidator validatorChain = new NameValidator();
    validatorChain
        .setNext(new PriceValidator())
        .setNext(new CategoryValidator());

    validatorChain.validate(p);  // ✅ runs full chain

    return "✅ Product passed all validations!";
}




}
