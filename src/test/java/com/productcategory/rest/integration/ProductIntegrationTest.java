package com.productcategory.rest.integration;

import com.productcategory.rest.domain.Product;
import com.productcategory.rest.repository.ProductRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ProductIntegrationTest extends AbstractIntegrationTest {

    @Inject
    private ProductRepository productRepository;
    private String url;

    @Before
    public void setUp() throws Exception {
        url = resourceUrl("/");
    }

    @Test
    public void shouldGetProducts() throws Exception {
        Product product = new Product("productName", BigDecimal.TEN, "productDescription", new Timestamp(new Date().getTime()), 2);
        productRepository.save(product);

        ResponseEntity<Product[]> response = restTemplate.getForEntity(url + "products", Product[].class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Collection<Product> products = asList(response.getBody());
        assertThat(products.iterator().next().getName(), is(product.getName()));
    }

    @Test
    public void shouldGetAProduct() throws Exception {
        Product product = new Product("productName", BigDecimal.TEN, "productDescription", new Timestamp(new Date().getTime()), 2);

        Product savedProduct = productRepository.save(product);

        ResponseEntity<Product> response = restTemplate.getForEntity(url + "products/{productId}", Product.class, savedProduct.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        System.out.println(savedProduct.getId());
    }

    @Test
    public void shouldCreateAProduct() throws Exception {
        Product product = new Product("productName", BigDecimal.TEN, "productDescription", new Timestamp(new Date().getTime()), 2);
        ResponseEntity<Product> createdProduct = restTemplate.postForEntity(url + "products", product, Product.class);

        Product retrievedProduct = productRepository.findOne(createdProduct.getBody().getId());

        assertThat(createdProduct.getBody().getId(), is(retrievedProduct.getId()));

        assertThat(createdProduct.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void shouldUpdateAProduct() throws Exception {
        Product product = new Product("productName", BigDecimal.TEN, "productDescription", new Timestamp(new Date().getTime()), 2);
        Product savedProduct = productRepository.save(product);
        savedProduct.setCategoryId(1);
        restTemplate.put(url + "products/{productId}", savedProduct, savedProduct.getId());

        assertThat(savedProduct.getCategoryId(), is(1));
    }

    @Test
    public void shouldDeleteAProduct() throws Exception {
        Product product = new Product("productName", BigDecimal.TEN, "productDescription", new Timestamp(new Date().getTime()), 2);
        Product savedProduct = productRepository.save(product);

        restTemplate.delete(url + "products/{productId}", savedProduct.getId());

        Product retrievedProduct = productRepository.findOne(savedProduct.getId());

        assertNull(retrievedProduct);
    }

    @After
    public void tearDown() throws Exception {
        productRepository.deleteAll();
    }

}
