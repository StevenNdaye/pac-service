package com.productcategory.rest.service;

import com.productcategory.rest.domain.Product;
import com.productcategory.rest.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.sql.Timestamp;

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

    @Override
    public Product updateProduct(int productId, Product product) {
        Product existingProduct = productRepository.findOne(productId);
        if(isNull(existingProduct)){
            return saveProduct(product);
        }
        return updateExistingProduct(product, existingProduct);
    }

    private Product updateExistingProduct(Product product, Product existingProduct) {
        existingProduct.setName(ifNotNull(product.getName(), existingProduct.getName()));
        existingProduct.setPrice(ifNotNull(product.getPrice(), existingProduct.getPrice()));
        existingProduct.setDescription(ifNotNull(product.getDescription(), existingProduct.getDescription()));
        existingProduct.setDate(ifNotNull(product.getDate(), existingProduct.getDate()));
        existingProduct.setCategoryId(ifNotNull(product.getCategoryId(), existingProduct.getCategoryId()));

        return productRepository.save(existingProduct);
    }

    private int ifNotNull(int data, int existingData) {
        return data == 0 ? existingData : data;
    }

    private Timestamp ifNotNull(Timestamp data, Timestamp existingData) {
        return data == null ? existingData : data;
    }

    private BigDecimal ifNotNull(BigDecimal data, BigDecimal existingData) {
        return data == null ? existingData : data;
    }

    private String ifNotNull(String data, String existingData) {
        return data == null ? existingData : data;
    }
}
