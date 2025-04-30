package com.product.flyweight;

import java.util.HashMap;
import java.util.Map;

public class MetadataFactory {
    private static final Map<String, ProductMetadata> cache = new HashMap<>();

    public static ProductMetadata getMetadata(String brand, String category) {
        String key = brand + "::" + category;
        return cache.computeIfAbsent(key, k -> {
            System.out.println("✨ Creating new metadata for: " + key);
            return new ProductMetadata(brand, category);
        });
    }

    public static int cacheSize() {
        return cache.size();
    }
}