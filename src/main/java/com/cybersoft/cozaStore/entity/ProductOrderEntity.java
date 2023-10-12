package com.cybersoft.cozaStore.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "product_order")
public class ProductOrderEntity {

    @ManyToOne
    @JoinColumn(name = "id_product")
    private ProductEntity product;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_order")
    private OrderEntity order;

    @Column(name = "quanity")
    private int quanity;

    @Column(name = "price")
    private double price;

    @Column(name = "create_date")
    private Date createDate;


    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
