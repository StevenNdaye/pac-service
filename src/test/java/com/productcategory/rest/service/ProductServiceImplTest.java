package com.productcategory.rest.service;

import com.productcategory.rest.domain.Product;
import com.productcategory.rest.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.productcategory.rest.helpers.SyntaxSugar.*;
import java.util.ArrayList;
import java.util.List;

import static com.productcategory.rest.domain.Product.Factory.createProduct;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by steven on 2014/11/02.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock private ProductRepository productRepository;
    private ProductService productService;
    private List<Product> products = new ArrayList<Product>();
    private Product product;

    @Before
    public void setUp(){
        productService = new ProductServiceImpl(productRepository);
        product = createProduct(PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_LAST_UPDATE, PRODUCT_CATEGORY_ID,
                PRODUCT_DESCRIPTION);
        products.add(product);
    }

    @Test
    public void itShouldGetAllProductsFromRepository(){
        when(productRepository.findAll()).thenReturn(products);
        Iterable<Product> returnedProducts = productService.getProducts();
        assertEquals(products, returnedProducts);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void itShouldGetAProductFromRepository(){
        when(productRepository.findOne(PRODUCT_ID)).thenReturn(product);
        Product returnedProduct = productService.getProduct(PRODUCT_ID);
        assertEquals(product, returnedProduct);
        verify(productRepository, times(1)).findOne(PRODUCT_ID);
    }

    @Test
    public void itShouldNotSaveProductWhenItExists(){
        when(productRepository.findByName(product.getName())).thenReturn(product);
        productService.saveProduct(product);
        verify(productRepository, times(1)).findByName(product.getName());
        verify(productRepository, times(0)).save(product);
    }

    @Test
    public void itShouldSaveProductIfNotExisting(){
        when(productRepository.findByName(product.getName())).thenReturn(null);
        when(productRepository.save(product)).thenReturn(product);
        Product returnedProduct = productService.saveProduct(product);
        assertEquals(product, returnedProduct);
        verify(productRepository, times(1)).findByName(product.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void itShouldDeleteAProductWhenGivenItsId(){
        when(productRepository.exists(PRODUCT_ID)).thenReturn(true);
        String message = productService.deleteProduct(PRODUCT_ID);
        assertEquals(PRODUCT_DELETED_MESSAGE, message);
        verify(productRepository, times(1)).delete(PRODUCT_ID);
    }

    @Test
    public void itShouldNotDeleteAProductIfNotExisting(){
        when(productRepository.exists(PRODUCT_ID)).thenReturn(false);
        String message = productService.deleteProduct(PRODUCT_ID);
        assertEquals(INVALID_PRODUCT_MESSAGE, message);
        verify(productRepository, times(0)).delete(PRODUCT_ID);
    }

    @Test
    public void itShouldUpdateProductWhenGivenItsId(){
        when(productRepository.findOne(PRODUCT_ID)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        Product updatedProduct = productService.updateProduct(PRODUCT_ID, product);
        assertEquals(product, updatedProduct);
        verify(productRepository, times(1)).save(updatedProduct);
    }
}
