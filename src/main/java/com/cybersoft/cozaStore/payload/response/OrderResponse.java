package com.cybersoft.cozaStore.payload.response;

import java.util.Date;

public class OrderResponse {
    private int id;

    private Date createDate;

    private String userName;

    private String statusName;

    public Date getCreateDate() {
        return createDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
