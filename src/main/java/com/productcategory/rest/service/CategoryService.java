package com.productcategory.rest.service;

import com.productcategory.rest.domain.Category;

/**
 * Created by steven on 2014/11/04.
 */
public interface CategoryService {
    Iterable<Category> getCategories();

    Category getCategory(int categoryId);

    Category saveCategory(Category category);

    String deleteCategory(int categoryId);

    Category updateCategory(int categoryId, Category category);

}
