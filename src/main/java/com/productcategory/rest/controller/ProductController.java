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
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    @Inject
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Product> getProducts() {
        return productService.getProducts();
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable int productId) throws ProductNotFoundException {
        return this.productService.getProduct(productId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Product saveProduct(@RequestBody @Valid Product product) {
        return productService.saveProduct(product);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
    public String deleteProduct(@PathVariable int productId) {
        return productService.deleteProduct(productId);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.PUT)
    public Product updateProduct(@PathVariable int productId, @RequestBody @Valid Product product) {
        return productService.updateProduct(productId, product);
    }
}
