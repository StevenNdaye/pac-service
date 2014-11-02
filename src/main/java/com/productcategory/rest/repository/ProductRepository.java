package com.productcategory.rest.repository;

import com.productcategory.rest.domain.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by steven on 2014/11/02.
 */
public interface ProductRepository  extends CrudRepository<Product, Integer>{
}
