package com.productcategory.rest.service;

import com.productcategory.rest.domain.Category;
import com.productcategory.rest.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by steven on 2014/11/04.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Inject
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(int categoryId) {
        return categoryRepository.findOne(categoryId);
    }
}
