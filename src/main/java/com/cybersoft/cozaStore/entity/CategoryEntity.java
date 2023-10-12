package com.cybersoft.cozaStore.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "create_date")
    private Date createDate;

    @OneToMany(mappedBy = "category")
    private List<ProductEntity> products;

    @OneToMany(mappedBy = "category")
    private List<CarouselEntity> carousels;

    public List<CarouselEntity> getCarousels() {
        return carousels;
    }


    public void setCarousels(List<CarouselEntity> carousels) {
        this.carousels = carousels;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }
}