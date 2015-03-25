package com.productcategory.rest.controller;

import com.productcategory.rest.domain.Category;
import com.productcategory.rest.exceptions.CategoryNotFoundException;
import com.productcategory.rest.service.CategoryService;
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

import static com.productcategory.rest.helpers.SyntaxSugar.CATEGORY_DELETED_MESSAGE;
import static com.productcategory.rest.helpers.SyntaxSugar.CATEGORY_ID;
import static com.productcategory.rest.helpers.SyntaxSugar.CATEGORY_NAME;
import static com.productcategory.rest.helpers.SyntaxSugar.INVALID_CATEGORY_MESSAGE;
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

    @Mock
    private CategoryService categoryService;
    private CategoryController categoryController;
    private List<Category> categories = new ArrayList<Category>();
    private Category category;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryController = new CategoryController(categoryService);
        category = new Category(CATEGORY_NAME);
        categories.add(category);
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void itShouldGetAllCategoriesFromService() {
        when(categoryService.getCategories()).thenReturn(categories);
        Iterable<Category> returnedCategories = categoryController.getCategories();
        assertEquals(categories, returnedCategories);
        verify(categoryService, times(1)).getCategories();
    }

    @Test
    public void itShouldGetACategoryFromService() {
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

    @Test
    public void itShouldSaveCategory() {
        when(categoryService.saveCategory(category)).thenReturn(category);
        Category returnedCategory = categoryController.saveCategory(category);
        assertEquals(category, returnedCategory);
        verify(categoryService, times(1)).saveCategory(category);
    }

    @Test
    public void itShouldDeleteAProductGivenItsId() {
        when(categoryService.deleteCategory(CATEGORY_ID)).thenReturn(CATEGORY_DELETED_MESSAGE);
        String message = categoryController.deleteCategory(CATEGORY_ID);
        assertEquals(CATEGORY_DELETED_MESSAGE, message);
        verify(categoryService, times(1)).deleteCategory(CATEGORY_ID);
    }

    @Test
    public void itShouldNotDeleteAProductGivenItsId() {
        when(categoryService.deleteCategory(CATEGORY_ID)).thenReturn(INVALID_CATEGORY_MESSAGE);
        String message = categoryController.deleteCategory(CATEGORY_ID);
        assertEquals(INVALID_CATEGORY_MESSAGE, message);
        verify(categoryService, times(1)).deleteCategory(CATEGORY_ID);
    }

    @Test
    public void itShouldUpdateProductWhenGivenItsId() {
        when(categoryService.updateCategory(CATEGORY_ID, category)).thenReturn(category);
        Category updatedCategory = categoryController.updateCategory(CATEGORY_ID, category);
        assertEquals(category, updatedCategory);
        verify(categoryService, times(1)).updateCategory(CATEGORY_ID, category);
    }

}
