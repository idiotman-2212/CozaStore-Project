package com.cybersoft.cozaStore.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "tag")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "create_date")
    private Date createDate;

    public List<BlogTagEntity> getBlogTags() {
        return blogTags;
    }

    public void setBlogTags(List<BlogTagEntity> blogTags) {
        this.blogTags = blogTags;
    }

    @OneToMany(mappedBy = "tag")
    private List<BlogTagEntity> blogTags;


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
}

