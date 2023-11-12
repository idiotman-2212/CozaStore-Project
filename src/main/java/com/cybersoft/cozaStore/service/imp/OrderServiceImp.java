package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.entity.OrderEntity;
import com.cybersoft.cozaStore.payload.response.OrderResponse;

import java.util.List;

public interface OrderServiceImp {
    List<OrderResponse> getOrder(int idOrder);


    List<OrderResponse> getAllOrder() ;

    boolean saveOrder(OrderEntity orderEntity);

    boolean deleteOrderById(int idOrder);

    List<OrderResponse> findOrderDetailByOrderId(int idOrder);

    List<OrderResponse> getOrderByIdUser(int idUser);

    Long maxIdOrder();
}
