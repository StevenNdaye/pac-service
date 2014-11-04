package com.productcategory.rest.controller;

import com.productcategory.rest.domain.Category;
import com.productcategory.rest.exceptions.CategoryNotFoundException;
import com.productcategory.rest.service.CategoryService;
import static com.productcategory.rest.helpers.SyntaxSugar.*;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by steven on 2014/11/04.
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {


    @Mock private CategoryService categoryService;
    private CategoryController categoryController;
    private List<Category> categories = new ArrayList<Category>();
    private Category category;
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        categoryController = new CategoryController(categoryService);
        category = Category.Factory.createCategory(CATEGORY_ID, CATEGORY_NAME);
        categories.add(category);
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void itShouldGetAllCategoriesFromService(){
        when(categoryService.getCategories()).thenReturn(categories);
        Iterable<Category> returnedCategories = categoryController.getCategories();
        assertEquals(categories, returnedCategories);
        verify(categoryService, times(1)).getCategories();
    }

    @Test
    public void itShouldGetACategoryFromService(){
        when(categoryService.getCategory(CATEGORY_ID)).thenReturn(category);
        Category returnedCategory = categoryController.getCategory(CATEGORY_ID);
        assertEquals(category, returnedCategory);
        verify(categoryService, times(1)).getCategory(CATEGORY_ID);
    }

    @Test
    public void itShouldHandleCategoryNotFoundScenario() throws Exception {
        when(categoryService.getCategory(CATEGORY_ID)).thenThrow(new CategoryNotFoundException(CATEGORY_ID));
        mockMvc.perform(get("/categories/{categoryId}", CATEGORY_ID)).andExpect(status().isNotFound());
        verify(categoryService, times(1)).getCategory(CATEGORY_ID);
    }

}
