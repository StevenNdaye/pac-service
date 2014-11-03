package com.productcategory.rest.service;

import com.productcategory.rest.domain.Product;
import com.productcategory.rest.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static java.util.Objects.isNull;

/**
 * Created by steven on 2014/11/02.
 */
@Service
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_DELETED_MESSAGE = "Product Deleted!";
    public static final String INVALID_PRODUCT_MESSAGE = "Invalid Product!";
    private final ProductRepository productRepository;

    @Inject
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(int productId) {
        return productRepository.findOne(productId);
    }

    @Override
    public Product saveProduct(Product product) {
        Product returnedProduct = productRepository.findByName(product.getName());
        if(isNull(returnedProduct)){
            return productRepository.save(product);
        }
        return returnedProduct;
    }

    @Override
    public String deleteProduct(int productId) {
        if(productRepository.exists(productId)){
            productRepository.delete(productId);
            return PRODUCT_DELETED_MESSAGE;
        }
        return INVALID_PRODUCT_MESSAGE;
    }
}
