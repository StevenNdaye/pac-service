package com.productcategory.rest.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by steven on 2014/11/02.
 */
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty
    private int id;

    @Column(name = "name")
    @JsonProperty
    private String name;

    @Column(name = "price")
    @JsonProperty
    private BigDecimal price;

    @Column(name = "description")
    @JsonProperty
    private String description;

    @Column(name = "last_update")
    @JsonProperty
    private Timestamp date;

    @Column(name = "category_id")
    @JsonProperty
    private int categoryId;

    public Product() {
    }

    @JsonCreator
    public Product(@JsonProperty("name") String name, @JsonProperty("price") BigDecimal price,
                   @JsonProperty("description") String description, @JsonProperty("date") Timestamp date,
                   @JsonProperty("categoryId") int categoryId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.date = date;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int  getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
