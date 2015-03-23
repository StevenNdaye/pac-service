package com.productcategory.rest.integration;

import com.jayway.restassured.RestAssured;
import com.productcategory.rest.Application;
import com.productcategory.rest.controller.ProductController;
import com.productcategory.rest.domain.Product;
import com.productcategory.rest.service.ProductService;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_CATEGORY_ID;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_DESCRIPTION;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_LAST_UPDATE;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_NAME;
import static com.productcategory.rest.helpers.SyntaxSugar.PRODUCT_PRICE;

@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ProductControllerIntegrationTest {


    @Mock
    private ProductService productService;
    private ProductController productController;
    private List<Product> products = new ArrayList<Product>();
    private Product product;

    @Value("$local.server.port")
    int port;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productController = new ProductController(productService);
        product = new Product(PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DESCRIPTION, PRODUCT_LAST_UPDATE, PRODUCT_CATEGORY_ID);
        products.add(product);

        RestAssured.port = port;
    }

//    @Test
//    public void itCanFetchProductsFromService(){
//        when().
//                get("/products").
//                then().
//                    statusCode(HttpStatus.SC_OK).
////                    assertThat
//
//    }
}
