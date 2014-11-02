package com.productcategory.rest.service;

import com.productcategory.rest.domain.Product;

import java.util.List;

/**
 * Created by steven on 2014/11/02.
 */
public interface ProductService {
    List<Product> getProducts();
}
