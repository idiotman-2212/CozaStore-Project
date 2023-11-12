package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.CartEntity;
import com.cybersoft.cozaStore.entity.OrderEntity;
import com.cybersoft.cozaStore.entity.ProductEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.response.CartResponse;
import com.cybersoft.cozaStore.payload.response.OrderResponse;
import com.cybersoft.cozaStore.payload.response.ProductResponse;
import com.cybersoft.cozaStore.payload.response.UserResponse;
import com.cybersoft.cozaStore.repository.OrderRepository;
import com.cybersoft.cozaStore.repository.UserRepository;
import com.cybersoft.cozaStore.service.imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderServiceImp {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public List<OrderResponse> getOrder(int idOrder) {
        Optional<OrderEntity> orderEntities = orderRepository.findById(idOrder);
        if (orderEntities.isPresent()) {
            OrderEntity orderEntity = orderEntities.get();
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(orderEntity.getId());
            orderResponse.setCreateDate(orderEntity.getCreateDate());

            List<OrderResponse> orderResponses = new ArrayList<>();
            orderResponses.add(orderResponse);

            return orderResponses;
        } else {
            // Trả về danh sách rỗng nếu không tìm thấy sản phẩm
            return Collections.emptyList();
        }
    }

    @Override
    public List<OrderResponse> getAllOrder() {

        List<OrderEntity> list = orderRepository.findAll();

        List<OrderResponse> responseList = new ArrayList<>();

        for (OrderEntity item: list) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(item.getId());
            orderResponse.setCreateDate(item.getCreateDate());

            orderResponse.setId_user(item.getUser().getId());
            orderResponse.setId_status(item.getStatus().getId());

            responseList.add(orderResponse);
        }

        return responseList;
    }

    @Override
    public boolean saveOrder(OrderEntity orderEntity) {
        return false;
    }

    @Override
    public boolean deleteOrderById(int idOrder) {
        return false;
    }

    @Override
    public List<OrderResponse> findOrderDetailByOrderId(int idOrder) {
        return null;
    }

    @Override
    public List<OrderResponse> getOrderByIdUser(int idUser) {
        return null;
    }

    @Override
    public Long maxIdOrder() {
        return null;
    }
}
