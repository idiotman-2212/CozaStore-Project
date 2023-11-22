package com.cybersoft.cozaStore.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;

import java.util.Date;

@Entity(name = "carousel")
public class CarouselEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "title")
        private String title;

        @Column(name = "image")
        private String image;

        @Column(name = "content")
        private String content;

        @ManyToOne
        @JoinColumn(name = "id_category")
        private CategoryEntity category;

        @Column(name = "create_date")
        private Date createDate;

    @PrePersist
    protected void onCreate() {
        createDate = new Date();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
