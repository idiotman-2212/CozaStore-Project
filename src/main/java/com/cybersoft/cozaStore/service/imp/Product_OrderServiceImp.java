package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.payload.response.ProductResponse;
import com.cybersoft.cozaStore.payload.response.ProductOrderResponse;

import java.math.BigDecimal;
import java.util.List;

public interface Product_OrderServiceImp {
    boolean insertProductOrder(ProductResponse productResponse) throws IllegalAccessException;

    List<ProductOrderResponse> getAllProductOrder();

    List<ProductOrderResponse> getProductOrderById(int id);

    List<ProductOrderResponse> updateProductOrder(int id, String productName, int id_order, int quanity, BigDecimal price);

    boolean deleteProductOrderById(int id);
}
