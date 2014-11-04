package com.productcategory.rest.service;

import com.productcategory.rest.domain.Category;
import com.productcategory.rest.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.productcategory.rest.helpers.SyntaxSugar.*;
import static com.productcategory.rest.helpers.SyntaxSugar.CATEGORY_ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by steven on 2014/11/04.
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;
    private List<Category> categories = new ArrayList<Category>();
    private Category category;

    @Before
    public void setUp(){
        categoryService = new CategoryServiceImpl(categoryRepository);
        category = Category.Factory.createCategory(CATEGORY_ID, CATEGORY_NAME);
        categories.add(category);
    }


    @Test
    public void itShouldGetAllCategoriesFromRepository(){
        when(categoryRepository.findAll()).thenReturn(categories);
        Iterable<Category> returnedCategories = categoryService.getCategories();
        assertEquals(categories, returnedCategories);
        verify(categoryRepository, times(1)).findAll();
    }
}
