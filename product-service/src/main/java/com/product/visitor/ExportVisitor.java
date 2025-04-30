package com.product.visitor;

import com.product.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExportVisitor implements ProductVisitor {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void visit(Product product) {
        try {
            String json = mapper.writeValueAsString(product);
            System.out.println("✅ Exported JSON: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
