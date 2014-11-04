package com.productcategory.rest.controller;

import com.productcategory.rest.domain.Category;
import com.productcategory.rest.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

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
}
