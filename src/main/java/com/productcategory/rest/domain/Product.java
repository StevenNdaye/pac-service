package com.productcategory.rest.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by steven on 2014/11/02.
 */
public class Product {

    private int id;
    private String name;
    private BigDecimal price;
    private LocalDate date;
    private int categoryId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public static class Factory {

        public static Product createProduct(final int id, final String name, final BigDecimal price, final LocalDate date, final int categoryId){
            return new Product(){{
                setId(id);
                setName(name);
                setPrice(price);
                setDate(date);
                setCategoryId(categoryId);
            }};
        }
    }
}
