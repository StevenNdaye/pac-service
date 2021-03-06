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

import static com.productcategory.rest.helpers.SyntaxSugar.INVALID_PRODUCT_MESSAGE;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_CATEGORY_ID;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_DELETED_MESSAGE;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_DESCRIPTION;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_ID;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_LAST_UPDATE;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_NAME;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_PRICE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by steven on 2014/11/02.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    private ProductService productService;
    private List<Product> products = new ArrayList<Product>();
    private Product product;

    @Before
    public void setUp() {
        productService = new ProductServiceImpl(productRepository);
        product = new Product(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DESCRIPTION, PRODUCT_LAST_UPDATE, PRODUCT_CATEGORY_ID);
        products.add(product);
    }

    @Test
    public void itShouldGetAllProductsFromRepository() {
        when(productRepository.findAll()).thenReturn(products);
        Iterable<Product> returnedProducts = productService.getProducts();
        assertEquals(products, returnedProducts);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void itShouldGetAProductFromRepository() {
        when(productRepository.findOne(PRODUCT_ID)).thenReturn(product);
        Product returnedProduct = productService.getProduct(PRODUCT_ID);
        assertEquals(product, returnedProduct);
        verify(productRepository, times(1)).findOne(PRODUCT_ID);
    }

    @Test
    public void itShouldNotSaveProductWhenItExists() {
        when(productRepository.findByName(product.getName())).thenReturn(product);
        productService.saveProduct(product);
        verify(productRepository, times(1)).findByName(product.getName());
        verify(productRepository, times(0)).save(product);
    }

    @Test
    public void itShouldSaveProductIfNotExisting() {
        when(productRepository.findByName(product.getName())).thenReturn(null);
        when(productRepository.save(product)).thenReturn(product);
        Product returnedProduct = productService.saveProduct(product);
        assertEquals(product, returnedProduct);
        verify(productRepository, times(1)).findByName(product.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void itShouldDeleteAProductWhenGivenItsId() {
        when(productRepository.exists(PRODUCT_ID)).thenReturn(true);
        String message = productService.deleteProduct(PRODUCT_ID);
        assertEquals(PRODUCT_DELETED_MESSAGE, message);
        verify(productRepository, times(1)).delete(PRODUCT_ID);
    }

    @Test
    public void itShouldNotDeleteAProductIfNotExisting() {
        when(productRepository.exists(PRODUCT_ID)).thenReturn(false);
        String message = productService.deleteProduct(PRODUCT_ID);
        assertEquals(INVALID_PRODUCT_MESSAGE, message);
        verify(productRepository, times(0)).delete(PRODUCT_ID);
    }

    @Test
    public void itShouldUpdateProductWhenGivenItsId() {
        when(productRepository.findOne(PRODUCT_ID)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        Product updatedProduct = productService.updateProduct(PRODUCT_ID, product);
        assertEquals(product, updatedProduct);
        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    public void itShouldNotUpdateNameWhenGivenNameIsNull() {
        Product expectedProduct = new Product("Jim", BigDecimal.ONE, "someDescription", new Timestamp(1415224029L), 1);
        Product someProduct = new Product(null, BigDecimal.ONE, "someDescription", new Timestamp(1415224029L), 1);

        when(productRepository.findOne(PRODUCT_ID)).thenReturn(expectedProduct);
        when(productRepository.save(expectedProduct)).thenReturn(expectedProduct);

        Product updatedProduct = productService.updateProduct(PRODUCT_ID, someProduct);

        assertEquals(updatedProduct.getName(), "Jim");
    }

    @Test
    public void itShouldNotUpdateDescriptionWhenGivenDescriptionIsNull() {
        Product expectedProduct = new Product("Jim", BigDecimal.ONE, "someDescription", new Timestamp(1415224029L), 1);
        Product someProduct = new Product(null, BigDecimal.ONE, "someDescription", new Timestamp(1415224029L), 1);

        when(productRepository.findOne(PRODUCT_ID)).thenReturn(expectedProduct);
        when(productRepository.save(expectedProduct)).thenReturn(expectedProduct);

        Product updatedProduct = productService.updateProduct(PRODUCT_ID, someProduct);

        assertEquals(updatedProduct.getDescription(), "someDescription");
    }

    @Test
    public void itShouldNotUpdatePriceWhenGivenPriceIsNull() {
        Product expectedProduct = new Product("Jim", BigDecimal.ONE, "someDescription", new Timestamp(1415224029L), 1);
        Product someProduct = new Product(null, BigDecimal.ONE, "someDescription", new Timestamp(1415224029L), 1);

        when(productRepository.findOne(PRODUCT_ID)).thenReturn(expectedProduct);
        when(productRepository.save(expectedProduct)).thenReturn(expectedProduct);

        Product updatedProduct = productService.updateProduct(PRODUCT_ID, someProduct);

        assertEquals(updatedProduct.getPrice(), BigDecimal.ONE);
    }

    @Test
    public void itShouldNotUpdateDateWhenGivenDateIsNull() {
        Timestamp timestamp = new Timestamp(1415224029L);
        Product expectedProduct = new Product("Jim", BigDecimal.ONE, "someDescription", timestamp, 1);
        Product someProduct = new Product(null, BigDecimal.ONE, "someDescription", timestamp, 1);

        when(productRepository.findOne(PRODUCT_ID)).thenReturn(expectedProduct);
        when(productRepository.save(expectedProduct)).thenReturn(expectedProduct);

        Product updatedProduct = productService.updateProduct(PRODUCT_ID, someProduct);

        assertEquals(updatedProduct.getDate(), timestamp);
    }
}
