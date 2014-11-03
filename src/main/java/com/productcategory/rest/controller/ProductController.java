package com.productcategory.rest.controller;

import com.productcategory.rest.domain.Product;
import com.productcategory.rest.exceptions.ProductNotFoundException;
import com.productcategory.rest.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

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
    public Product getProduct(@PathVariable int productId) throws ProductNotFoundException {
        return this.productService.getProduct(productId);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public Product saveProduct(@RequestBody @Valid Product product) {
        return productService.saveProduct(product);
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.DELETE)
    public String deleteProduct(@PathVariable int productId) {
        return productService.deleteProduct(productId);
    }
}
