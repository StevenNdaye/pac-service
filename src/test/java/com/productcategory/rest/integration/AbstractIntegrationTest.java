package com.productcategory.rest.integration;

import com.productcategory.rest.ApplicationConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestApplicationConfiguration.class, ApplicationConfiguration.class})
@WebAppConfiguration
@IntegrationTest("server.port:0")
public abstract class AbstractIntegrationTest {
    protected static final String BASE_URL_TEMPLATE = "http://localhost:%d";

    @Inject
    protected RestTemplate restTemplate;

    @Value("${local.server.port}")
    protected int port;

    protected String resourceUrl(String resourcePath) {
        return String.format(BASE_URL_TEMPLATE, port) + resourcePath;
    }
}
