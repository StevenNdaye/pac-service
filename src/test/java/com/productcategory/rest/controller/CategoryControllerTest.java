package com.productcategory.rest.controller;

import com.productcategory.rest.domain.Category;
import com.productcategory.rest.service.CategoryService;
import static com.productcategory.rest.helpers.SyntaxSugar.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by steven on 2014/11/04.
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {


    @Mock private CategoryService categoryService;
    private CategoryController categoryController;
    private List<Category> categories = new ArrayList<Category>();
    private Category category;

    @Before
    public void setUp(){
        categoryController = new CategoryController(categoryService);
        category = Category.Factory.createCategory(CATEGORY_ID, CATEGORY_NAME);
        categories.add(category);
    }

    @Test
    public void itShouldGetAllCategories(){
        when(categoryService.getCategories()).thenReturn(categories);
        Iterable<Category> returnedCategories = categoryController.getCategories();
        assertEquals(categories, returnedCategories);
        verify(categoryService, times(1)).getCategories();
    }

}
