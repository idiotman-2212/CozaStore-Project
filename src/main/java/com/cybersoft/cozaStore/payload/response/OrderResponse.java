package com.cybersoft.cozaStore.payload.response;

import java.util.Date;

public class OrderResponse {
    private int id;

    private Date createDate;

    private int id_user;

    private int id_status;

    public Date getCreateDate() {
        return createDate;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public Date getCreateDate(Date createDate) {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
