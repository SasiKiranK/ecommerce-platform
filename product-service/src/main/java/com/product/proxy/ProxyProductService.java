package com.product.proxy;

import com.product.model.Product;

import java.util.HashSet;
import java.util.Set;

public class ProxyProductService implements ProductService {

    private final RealProductService realService = new RealProductService();
    private final Set<String> blockedIds = Set.of("secret", "blocked");

    @Override
    public Product getProductById(String id) {
        System.out.println("🕵 Proxy: Checking access for ID: " + id);

        if (blockedIds.contains(id.toLowerCase())) {
            System.out.println("❌ Access denied to product: " + id);
            return null;
        }

        return realService.getProductById(id); // forward the call
    }
}
