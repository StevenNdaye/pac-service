package com.productcategory.rest.controller;

import com.productcategory.rest.domain.Product;
import com.productcategory.rest.service.ProductService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        products.add(createProduct(100, "First Product", BigDecimal.TEN, LocalDate.now(), 10));
        products.add(createProduct(101, "SecondProduct", BigDecimal.ONE, LocalDate.now(), 11));
    }

    @Test
    public void itShouldGetAllProducts(){
        when(productService.getProducts()).thenReturn(products);
        List<Product> returnedProducts = productController.getProducts();

        assertEquals(returnedProducts, products);
        verify(productService, times(1)).getProducts();

    }

    public Product createProduct(final int id, final String name, final BigDecimal price, final LocalDate date, final int categoryId){
        return new Product(){{
            setId(id);
            setName(name);
            setPrice(price);
            setDate(date);
            setCategoryId(categoryId);
        }};
    }
}
