package com.productcategory.rest.service;

import com.productcategory.rest.domain.Product;
import com.productcategory.rest.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
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

    public static final int PRODUCT_ID = 100;
    public static final String PRODUCT_NAME = "A product";
    public static final BigDecimal PRODUCT_PRICE = BigDecimal.TEN;
    public static final Timestamp PRODUCT_LAST_UPDATE = new Timestamp(System.currentTimeMillis());
    public static final int PRODUCT_CATEGORY_ID = 10;
    public static final String PRODUCT_DESCRIPTION = "A product description";

    @Mock private ProductRepository productRepository;
    private ProductService productService;
    private List<Product> products = new ArrayList<Product>();
    private Product product;

    @Before
    public void setUp(){
        productService = new ProductServiceImpl(productRepository);
        product = createProduct(PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_LAST_UPDATE, PRODUCT_CATEGORY_ID, PRODUCT_DESCRIPTION);
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
}
