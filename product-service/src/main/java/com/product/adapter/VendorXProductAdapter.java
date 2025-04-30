package com.product.adapter;

import com.product.adapter.external.VendorXProduct;
import com.product.model.Product;

public class VendorXProductAdapter implements ProductAdapter {

    private final VendorXProduct vendorXProduct;

    public VendorXProductAdapter(VendorXProduct vendorXProduct) {
        this.vendorXProduct = vendorXProduct;
    }

    @Override
    public Product convert() {
        return new Product(
            vendorXProduct.productTitle,
            vendorXProduct.details,
            vendorXProduct.cost,
            vendorXProduct.group
        );
    }
}
