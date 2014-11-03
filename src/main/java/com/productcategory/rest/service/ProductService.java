package com.productcategory.rest.service;

import com.productcategory.rest.domain.Product;

/**
 * Created by steven on 2014/11/02.
 */
public interface ProductService {
    Iterable<Product> getProducts();

    Product getProduct(int productId);

    Product saveProduct(Product product);
}
