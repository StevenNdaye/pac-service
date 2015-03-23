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

import static com.productcategory.rest.helpers.SyntaxSugar.INVALID_PRODUCT_MESSAGE;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_CATEGORY_ID;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_DELETED_MESSAGE;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_DESCRIPTION;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_ID;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_LAST_UPDATE;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_NAME;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_PRICE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by steven on 2014/11/02.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;
    private ProductController productController;
    private List<Product> products = new ArrayList<Product>();
    private Product product;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productController = new ProductController(productService);
        product = new Product(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DESCRIPTION, PRODUCT_LAST_UPDATE, PRODUCT_CATEGORY_ID);
        products.add(product);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void itShouldGetAllProductsFromService() {
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

    @Test
    public void itShouldSaveProduct() throws Exception {
        when(productService.saveProduct(product)).thenReturn(product);
        Product returnedProduct = productController.saveProduct(product);
        assertEquals(product, returnedProduct);
        verify(productService, times(1)).saveProduct(product);
    }

    @Test
    public void itShouldDeleteAProductGivenItsId() {
        when(productService.deleteProduct(PRODUCT_ID)).thenReturn(PRODUCT_DELETED_MESSAGE);
        String message = productController.deleteProduct(PRODUCT_ID);
        assertEquals(PRODUCT_DELETED_MESSAGE, message);
        verify(productService, times(1)).deleteProduct(PRODUCT_ID);
    }

    @Test
    public void itShouldNotDeleteAProductGivenItsId() {
        when(productService.deleteProduct(PRODUCT_ID)).thenReturn(INVALID_PRODUCT_MESSAGE);
        String message = productController.deleteProduct(PRODUCT_ID);
        assertEquals(INVALID_PRODUCT_MESSAGE, message);
        verify(productService, times(1)).deleteProduct(PRODUCT_ID);
    }

    @Test
    public void itShouldUpdateProductWhenGivenItsId() {
        when(productService.updateProduct(PRODUCT_ID, product)).thenReturn(product);
        Product updatedProduct = productController.updateProduct(PRODUCT_ID, product);
        assertEquals(product, updatedProduct);
        verify(productService, times(1)).updateProduct(PRODUCT_ID, product);
    }
}
