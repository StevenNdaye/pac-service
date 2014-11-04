package com.productcategory.rest.service;

import com.productcategory.rest.domain.Category;
import com.productcategory.rest.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * Created by steven on 2014/11/04.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORY_DELETED_MESSAGE = "Category Deleted!";
    private static final String INVALID_CATEGORY_MESSAGE = "Invalid Category!";
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

    @Override
    public Category saveCategory(Category category) {
        Category returnedCategory = categoryRepository.findByName(category.getName());
        if(Objects.isNull(returnedCategory)){
            return categoryRepository.save(category);
        }
        return returnedCategory;
    }

    @Override
    public String deleteCategory(int categoryId) {
        if(categoryRepository.exists(categoryId)){
            categoryRepository.delete(categoryId);
            return CATEGORY_DELETED_MESSAGE;
        }
        return INVALID_CATEGORY_MESSAGE;
    }

    @Override
    public Category updateCategory(int categoryId, Category category) {
        Category existingCategory = categoryRepository.findOne(categoryId);
        if(isNull(existingCategory)){
            return saveCategory(category);
        }
        return updateExistingCategory(category, existingCategory);
    }

    private Category updateExistingCategory(Category category, Category existingCategory) {
        existingCategory.setName(ifNotNull(category.getName(), existingCategory.getName()));
        return categoryRepository.save(existingCategory);
    }

    private String ifNotNull(String name, String existingCategoryName) {
        return name.equals(null) ? existingCategoryName : name;
    }
}
