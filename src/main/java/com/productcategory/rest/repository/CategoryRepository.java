package com.productcategory.rest.repository;

import com.productcategory.rest.domain.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by steven on 2014/11/04.
 */
public interface CategoryRepository extends CrudRepository<Category, Integer>{

}
