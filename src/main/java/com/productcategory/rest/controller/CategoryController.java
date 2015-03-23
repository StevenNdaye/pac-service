package com.productcategory.rest.controller;

import com.productcategory.rest.domain.Category;
import com.productcategory.rest.exceptions.CategoryNotFoundException;
import com.productcategory.rest.service.CategoryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by steven on 2014/11/04.
 */
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Inject
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Category> getCategories() {
        return categoryService.getCategories();
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public Category getCategory(@PathVariable int categoryId) throws CategoryNotFoundException {
        return categoryService.getCategory(categoryId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Category saveCategory(@RequestBody @Valid Category category) {
        return categoryService.saveCategory(category);
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.DELETE)
    public String deleteCategory(@PathVariable int categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.PUT)
    public Category updateCategory(@PathVariable int categoryId, @RequestBody @Valid Category category) {
        return categoryService.updateCategory(categoryId, category);
    }
}
