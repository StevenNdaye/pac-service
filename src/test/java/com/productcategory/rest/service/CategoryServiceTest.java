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

import static com.productcategory.rest.helpers.SyntaxSugar.CATEGORY_DELETED_MESSAGE;
import static com.productcategory.rest.helpers.SyntaxSugar.CATEGORY_ID;
import static com.productcategory.rest.helpers.SyntaxSugar.CATEGORY_NAME;
import static com.productcategory.rest.helpers.SyntaxSugar.INVALID_CATEGORY_MESSAGE;
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
    public void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository);
        category = new Category(CATEGORY_NAME);
        categories.add(category);
    }

    @Test
    public void itShouldGetAllCategoriesFromRepository() {
        when(categoryRepository.findAll()).thenReturn(categories);
        Iterable<Category> returnedCategories = categoryService.getCategories();
        assertEquals(categories, returnedCategories);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void itShouldGetACategoryFromRepository() {
        when(categoryRepository.findOne(CATEGORY_ID)).thenReturn(category);
        Category returnedCategory = categoryService.getCategory(CATEGORY_ID);
        assertEquals(category, returnedCategory);
        verify(categoryRepository, times(1)).findOne(CATEGORY_ID);
    }

    @Test
    public void itShouldSaveCategoryIfNotExists() {
        when(categoryRepository.findByName(category.getName())).thenReturn(null);
        when(categoryRepository.save(category)).thenReturn(category);
        Category returnedCategory = categoryService.saveCategory(category);
        assertEquals(category, returnedCategory);
        verify(categoryRepository, times(1)).findByName(category.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void itShouldNotSaveCategoryWhenItExists() {
        when(categoryRepository.findByName(category.getName())).thenReturn(category);
        categoryService.saveCategory(category);
        verify(categoryRepository, times(1)).findByName(category.getName());
        verify(categoryRepository, times(0)).save(category);
    }

    @Test
    public void itShouldDeleteAProductWhenGivenItsId() {
        when(categoryRepository.exists(CATEGORY_ID)).thenReturn(true);
        String message = categoryService.deleteCategory(CATEGORY_ID);
        assertEquals(CATEGORY_DELETED_MESSAGE, message);
        verify(categoryRepository, times(1)).delete(CATEGORY_ID);
    }

    @Test
    public void itShouldNotDeleteAProductIfNotExisting() {
        when(categoryRepository.exists(CATEGORY_ID)).thenReturn(false);
        String message = categoryService.deleteCategory(CATEGORY_ID);
        assertEquals(INVALID_CATEGORY_MESSAGE, message);
        verify(categoryRepository, times(0)).delete(CATEGORY_ID);
    }

    @Test
    public void itShouldUpdateProductWhenGivenItsId() {
        when(categoryRepository.findOne(CATEGORY_ID)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        Category updatedCategory = categoryService.updateCategory(CATEGORY_ID, category);
        assertEquals(category, updatedCategory);
        verify(categoryRepository, times(1)).save(updatedCategory);
    }

    @Test
    public void itShouldNotUpdateNameWhenGivenNameIsNull() {
        Category expectedCategory = new Category("James");
        Category someCategory = new Category(null);

        when(categoryRepository.findOne(CATEGORY_ID)).thenReturn(expectedCategory);
        when(categoryRepository.save(expectedCategory)).thenReturn(expectedCategory);

        Category updatedCategory = categoryService.updateCategory(CATEGORY_ID, someCategory);

        assertEquals(updatedCategory.getName(), "James");
    }
}
