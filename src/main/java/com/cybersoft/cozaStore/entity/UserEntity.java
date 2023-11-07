package com.cybersoft.cozaStore.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name = "create_date")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;

    @OneToMany(mappedBy = "user")
    private List<BlogEntity> blogs;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders;

    @OneToMany(mappedBy = "user")
    private List<CartEntity> carts;

    @OneToMany(mappedBy = "user")
    private List<PasswordResetTokenEntity> resetPasswords;

    public List<PasswordResetTokenEntity> getResetPasswords() {
        return resetPasswords;
    }

    public void setResetPasswords(List<PasswordResetTokenEntity> resetPasswords) {
        this.resetPasswords = resetPasswords;
    }

    public List<BlogEntity> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<BlogEntity> blogs) {
        this.blogs = blogs;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public List<CartEntity> getCarts() {
        return carts;
    }

    public void setCarts(List<CartEntity> carts) {
        this.carts = carts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
}
