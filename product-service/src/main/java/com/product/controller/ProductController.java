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

@GetMapping("/visitor-test")
public String visitorDemo() {
    Product p = new Product("Camera", "DSLR", 1200.0, "electronics");

    // Apply different operations via visitors
    p.accept(new ExportVisitor());
    p.accept(new AuditVisitor());
    p.accept(new TaxCalculatorVisitor());

    return "✅ Visitors applied to product!";
}

@GetMapping("/memento-demo")
public String mementoDemo() {
    Product product = new Product("Chair", "Wooden chair", 150.0, "furniture");
    ProductHistory history = new ProductHistory();

    // Save original
    history.save(product.save());

    // Modify 1
    product.setPrice(100);
    product.setName("Discounted Chair");

    // Save modified
    history.save(product.save());

    // Modify 2
    product.setPrice(70);
    product.setCategory("clearance");

    System.out.println("💥 Current: " + product.getName() + ", ₹" + product.getPrice());

    // Undo to previous
    product.restore(history.undo());
    System.out.println("↩ Undo 1: " + product.getName() + ", ₹" + product.getPrice());

    // Undo to original
    product.restore(history.undo());
    System.out.println("↩ Undo 2: " + product.getName() + ", ₹" + product.getPrice());

    return "✅ Memento pattern test complete. Check console!";
}

@GetMapping("/adapter-demo")
public String adapterTest() {
    VendorXProduct vendorProduct = new VendorXProduct("Dell XPS", "16GB RAM, 512 SSD", 1350.0, "electronics");

    ProductAdapter adapter = new VendorXProductAdapter(vendorProduct);
    Product p = adapter.convert();

    return "✅ Adapted: " + p.getName() + ", ₹" + p.getPrice() + ", category: " + p.getCategory();
}


@GetMapping("/bridge-demo")
public String bridgeTest() {
    Product physical = new Product("T-Shirt", "Cotton", 499, "clothing");
    Product digital = new Product("E-Book", "Learn Java", 299, "digital");

    ProductSaver physicalSaver = new PhysicalProductSaver(new MongoProductPersistence());
    ProductSaver digitalSaver = new DigitalProductSaver(new PostgresProductPersistence());

    physicalSaver.saveProduct(physical);
    digitalSaver.saveProduct(digital);

    return "✅ Bridge Pattern demo complete! Check logs.";
}


@GetMapping("/proxy-demo")
public String proxyDemo(@RequestParam String id) {
    ProductService service = new ProxyProductService();

    Product product = service.getProductById(id);

    if (product == null) return "❌ Product access blocked!";
    return "✅ Product: " + product.getName() + " ₹" + product.getPrice();
}

@GetMapping("/flyweight-demo")
public String flyweightDemo() {
    Product p1 = new Product("iPhone 14", 79999, MetadataFactory.getMetadata("Apple", "Electronics"));
    Product p2 = new Product("MacBook Pro", 199999, MetadataFactory.getMetadata("Apple", "Electronics"));
    Product p3 = new Product("AirPods", 19999, MetadataFactory.getMetadata("Apple", "Electronics"));
    Product p4 = new Product("T-Shirt", 499, MetadataFactory.getMetadata("Zara", "Clothing"));

    StringBuilder sb = new StringBuilder();
    sb.append(p1.getDetails()).append("\n");
    sb.append(p2.getDetails()).append("\n");
    sb.append(p3.getDetails()).append("\n");
    sb.append(p4.getDetails()).append("\n");

    sb.append("🧠 Metadata cache size: ").append(MetadataFactory.cacheSize());

    return "<pre>" + sb + "</pre>";
}


@GetMapping("/facade-demo")
public String facadeDemo() {
    Product p = new Product("Laptop", "Intel i7", 999.0, "electronics");
    p.setBrand("Apple");

    ProductCreationFacade facade = new ProductCreationFacade();
    facade.createProduct(p);

    return "✅ Product created via Facade!";
}


@GetMapping("/decorator-demo")
public String decoratorDemo() {
    Product product = new Product("Smartphone", "Android phone", 10000.0, "electronics");

    PriceCalculator calc = new BasePriceCalculator();
    calc = new GstTaxDecorator(calc);          // add 18% GST
    calc = new ShippingDecorator(calc);        // add ₹49 shipping
    calc = new DiscountDecorator(calc);        // subtract 10% discount

    double finalPrice = calc.calculate(product);

    return "Final decorated price: ₹" + finalPrice;
}




}
