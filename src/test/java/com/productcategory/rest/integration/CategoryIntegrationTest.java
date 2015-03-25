package com.productcategory.rest.integration;

import com.productcategory.rest.domain.Category;
import com.productcategory.rest.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CategoryIntegrationTest extends AbstractIntegrationTest{

    @Inject
    private CategoryRepository categoryRepository;
    private String url;

    @Before
    public void setUp() throws Exception {
        url = resourceUrl("/");
    }

    @Test
    @Ignore
    public void shouldGetCategories() throws Exception {
        Category category = new Category("Category");
        categoryRepository.save(category);

        ResponseEntity<Category[]> response = restTemplate.getForEntity(url + "categories/", Category[].class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Collection<Category> categories = asList(response.getBody());
        assertThat(categories.iterator().next().getName(), is(category.getName()));
    }
}
