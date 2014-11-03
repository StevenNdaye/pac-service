package com.productcategory.rest.controller;

import com.productcategory.rest.domain.Product;
import com.productcategory.rest.exceptions.ProductNotFoundException;
import com.productcategory.rest.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.productcategory.rest.domain.Product.Factory.createProduct;
import static com.productcategory.rest.helpers.SyntaxSugar.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by steven on 2014/11/02.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock private ProductService productService;
    private ProductController productController;
    private List<Product> products = new ArrayList<Product>();
    private Product product;
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        productController = new ProductController(productService);
        product = createProduct(PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_LAST_UPDATE, PRODUCT_CATEGORY_ID, PRODUCT_DESCRIPTION);
        products.add(product);

        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void itShouldGetAllProductsFromService(){
        when(productService.getProducts()).thenReturn(products);
        Iterable<Product> returnedProducts = productController.getProducts();

        assertEquals(products, returnedProducts);
        verify(productService, times(1)).getProducts();
    }

    @Test
    public void itShouldGetAProductFromService() throws ProductNotFoundException {
        when(productService.getProduct(any(Integer.class))).thenReturn(product);
        Product returnedProduct = productController.getProduct(PRODUCT_ID);

        assertEquals(product, returnedProduct);
        verify(productService, times(1)).getProduct(PRODUCT_ID);
    }

    @Test
    public void itShouldHandleProductNotFoundScenario() throws Exception {
        when(productService.getProduct(PRODUCT_ID)).thenThrow(new ProductNotFoundException(PRODUCT_ID));
        mockMvc.perform(get("/products/{productId}", PRODUCT_ID)).andExpect(status().isNotFound());
        verify(productService, times(1)).getProduct(PRODUCT_ID);
    }
}
