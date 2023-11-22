package com.cybersoft.cozaStore.payload.response;

import java.util.List;

public class CartSummaryResponse {
    private List<CartResponse> cartResponses;
    private double totalCostCart;

    public List<CartResponse> getCartResponses() {
        return cartResponses;
    }

    public void setCartResponses(List<CartResponse> cartResponses) {
        this.cartResponses = cartResponses;
    }

    public double getTotalCostCart() {
        return totalCostCart;
    }

    public void setTotalCostCart(double totalCostCart) {
        this.totalCostCart = totalCostCart;
    }
}
