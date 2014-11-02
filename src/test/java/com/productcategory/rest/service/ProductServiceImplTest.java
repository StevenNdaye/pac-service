package com.productcategory.rest.service;

import com.productcategory.rest.domain.Product;
import com.productcategory.rest.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Before
    public void setUp(){
        productService = new ProductServiceImpl(productRepository);
        products.add(createProduct(100, "First Product", BigDecimal.TEN, LocalDate.now(), 10));
        products.add(createProduct(101, "SecondProduct", BigDecimal.ONE, LocalDate.now(), 11));
    }

    @Test
    public void itShouldGetAllProductsFromRepository(){
        when(productRepository.findAll()).thenReturn(products);
        List<Product> returnedProducts = productService.getProducts();

        assertEquals(products, returnedProducts);

        verify(productRepository, times(1)).findAll();
    }
}
