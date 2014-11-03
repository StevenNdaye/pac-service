package com.productcategory.rest.controller;

import com.productcategory.rest.domain.Product;
import com.productcategory.rest.service.ProductService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by steven on 2014/11/02.
 */
@RestController
public class ProductController {

    private final ProductService productService;

    @Inject
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public Iterable<Product> getProducts() {
        return productService.getProducts();
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable int productId) {
        return productService.getProduct(productId);
    }
}
