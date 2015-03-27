package com.productcategory.rest.integration;

import com.productcategory.rest.domain.Category;
import com.productcategory.rest.repository.CategoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@Ignore
public class CategoryIntegrationTest extends AbstractIntegrationTest {

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

        ResponseEntity<Category[]> response = restTemplate.getForEntity(url + "categories", Category[].class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Collection<Category> categories = asList(response.getBody());
        System.out.println(categories.size());
        assertThat(categories.iterator().next().getName(), is(category.getName()));
    }

    @Test
    @Ignore
    public void shouldGetACategory() throws Exception {
        Category category = new Category("Category");

        Category savedCategory = categoryRepository.save(category);

        ResponseEntity<Category> response = restTemplate.getForEntity(url + "categories/{categoryId}", Category.class, savedCategory.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        System.out.println(savedCategory.getId());
    }

    @Test
    @Ignore
    public void shouldCreateACategory() throws Exception {
        Category category = new Category("Category");
        ResponseEntity<Category> createdCategory = restTemplate.postForEntity(url + "categories", category, Category.class);

        Category retrievedCategory = categoryRepository.findOne(createdCategory.getBody().getId());

        assertThat(createdCategory.getBody().getId(), is(retrievedCategory.getId()));

        assertThat(createdCategory.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    @Ignore
    public void shouldUpdateACategory() throws Exception {
        Category category = new Category("Category");
        Category savedCategory = categoryRepository.save(category);
        savedCategory.setId(1);
        restTemplate.put(url + "categories/{categoryId}", savedCategory, savedCategory.getId());

        assertThat(savedCategory.getId(), is(1));
    }

    @Test
    @Ignore
    public void shouldDeleteACategory() throws Exception {
        Category category = new Category("Category");
        Category savedCategory = categoryRepository.save(category);

        restTemplate.delete(url + "categories/{categoryId}", savedCategory.getId());

        Category retrievedCategory = categoryRepository.findOne(savedCategory.getId());

        assertNull(retrievedCategory);
    }

    @After
    public void tearDown() throws Exception {
        categoryRepository.deleteAll();
    }
}
