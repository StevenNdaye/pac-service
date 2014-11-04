package com.productcategory.rest.domain;

import javax.persistence.*;

/**
 * Created by steven on 2014/11/04.
 */
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

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

    public static class Factory {
        public static Category createCategory(int id, String name){
            return new Category(){{
                setId(id);
                setName(name);
            }};
        }
    }
}
