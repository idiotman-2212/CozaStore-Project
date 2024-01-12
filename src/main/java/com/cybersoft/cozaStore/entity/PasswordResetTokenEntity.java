package com.cybersoft.cozaStore.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "reset_password")
public class PasswordResetTokenEntity {
    public static final int EXPIRATION_MINUTES  = 60 * 24;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "token")
    private String token;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;

    public PasswordResetTokenEntity() {
        // Constructor mặc định được yêu cầu bởi JPA
    }

    public PasswordResetTokenEntity(String token, UserEntity user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION_MINUTES);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Date now = new Date();
        return new Date(now.getTime() + expiryTimeInMinutes * 60 * 1000);
    }
}
