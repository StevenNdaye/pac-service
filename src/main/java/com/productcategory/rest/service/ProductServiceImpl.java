package com.productcategory.rest.service;

import com.productcategory.rest.domain.Product;
import com.productcategory.rest.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.productcategory.rest.util.Utils.ifNotNull;
import static java.util.Objects.isNull;

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
        if (isNull(returnedProduct)) {
            return productRepository.save(product);
        }
        return returnedProduct;
    }

    @Override
    public String deleteProduct(int productId) {
        if (productRepository.exists(productId)) {
            productRepository.delete(productId);
            return PRODUCT_DELETED_MESSAGE;
        }
        return INVALID_PRODUCT_MESSAGE;
    }

    @Override
    public Product updateProduct(int productId, Product product) {
        Product existingProduct = productRepository.findOne(productId);
        if (isNull(existingProduct)) {
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
}
