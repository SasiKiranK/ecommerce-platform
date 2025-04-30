package com.product.proxy;

import com.product.model.Product;

public class RealProductService implements ProductService {
    @Override
    public Product getProductById(String id) {
        System.out.println("📦 Fetching product from real service: " + id);
        return new Product("Laptop", "Intel i7", 999.99, "electronics");
    }
}
