package com.cybersoft.cozaStore.payload.response;

public class CartResponse {
    private int idCart;
    private double totalCostProduct;
    private int quanity;
    private String nameProduct;
    private String nameUser;

    // Thêm trường mới

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }


    public double getTotalCostProduct() {
        return totalCostProduct;
    }

    public void setTotalCostProduct(double totalCostProduct) {
        this.totalCostProduct = totalCostProduct;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

}

