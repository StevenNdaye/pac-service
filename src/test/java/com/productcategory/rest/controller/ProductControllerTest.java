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

    public static final int PRODUCT_ID = 100;
    public static final String PRODUCT_NAME = "A product";
    public static final BigDecimal PRODUCT_PRICE = BigDecimal.TEN;
    public static final Timestamp PRODUCT_LAST_UPDATE = new Timestamp(System.currentTimeMillis());
    public static final int PRODUCT_CATEGORY_ID = 10;
    public static final String PRODUCT_DESCRIPTION = "A product description";

    @Mock private ProductService productService;
    private ProductController productController;
    private List<Product> products = new ArrayList<Product>();
    private Product product;

    @Before
    public void setUp(){
        productController = new ProductController(productService);
        product = createProduct(PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_LAST_UPDATE, PRODUCT_CATEGORY_ID, PRODUCT_DESCRIPTION);
        products.add(product);
    }

    @Test
    public void itShouldGetAllProductsFromService(){
        when(productService.getProducts()).thenReturn(products);
        Iterable<Product> returnedProducts = productController.getProducts();

        assertEquals(products, returnedProducts);
        verify(productService, times(1)).getProducts();
    }

    @Test
    public void itShouldGetAProductFromService(){
        when(productService.getProduct(any(Integer.class))).thenReturn(product);
        Product returnedProduct = productController.getProduct(PRODUCT_ID);

        assertEquals(product, returnedProduct);
        verify(productService, times(1)).getProduct(PRODUCT_ID);
    }
}
