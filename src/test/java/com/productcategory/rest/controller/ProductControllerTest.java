package com.productcategory.rest.controller;

import com.productcategory.rest.domain.Product;
import com.productcategory.rest.service.ProductService;
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
import static org.mockito.Mockito.*;

/**
 * Created by steven on 2014/11/02.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock private ProductService productService;
    private ProductController productController;
    private List<Product> products = new ArrayList<Product>();

    @Before
    public void setUp(){
        productController = new ProductController(productService);
        products.add(createProduct(100, "First Product", BigDecimal.TEN, new Timestamp(System.currentTimeMillis()),
                10, "description"));
        products.add(createProduct(101, "SecondProduct", BigDecimal.ONE, new Timestamp(System.currentTimeMillis()),
                11, "description"));
    }

    @Test
    public void itShouldGetAllProductsFromService(){
        when(productService.getProducts()).thenReturn(products);
        Iterable<Product> returnedProducts = productController.getProducts();

        assertEquals(products, returnedProducts);

        verify(productService, times(1)).getProducts();

    }
}
