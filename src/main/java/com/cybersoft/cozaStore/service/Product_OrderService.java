package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.*;
import com.cybersoft.cozaStore.entity.keys.ProductOrderKeys;
import com.cybersoft.cozaStore.payload.response.ProductResponse;
import com.cybersoft.cozaStore.payload.response.ProductOrderResponse;
import com.cybersoft.cozaStore.repository.OrderRepository;
import com.cybersoft.cozaStore.repository.ProductRepository;
import com.cybersoft.cozaStore.repository.Product_OrderRepository;
import com.cybersoft.cozaStore.service.imp.Product_OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class Product_OrderService implements Product_OrderServiceImp {
    @Autowired
    private Product_OrderRepository product_orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public boolean insertProductOrder(ProductResponse productResponse) throws IllegalAccessException {
        Optional<ProductEntity> product = productRepository.findById(productResponse.getId());
        Optional<OrderEntity> order = orderRepository.findById(productResponse.getId());

        if(product.isPresent() && order.isPresent()){

            ProductOrderEntity productOrderEntity;

            // Nếu sản phẩm chưa tồn tại, tạo một mục mới
            productOrderEntity = new ProductOrderEntity();

            ProductOrderKeys productOrderKeys = new ProductOrderKeys();
            productOrderKeys.setOrder(order.get());
            productOrderKeys.setProduct(product.get());
            productOrderEntity.setKeys(productOrderKeys);

            productOrderEntity.setPrice((productOrderEntity.getPrice()));
            productOrderEntity.setQuanity(productOrderEntity.getQuanity());
            productOrderEntity.setCreateDate(new Date());


            try {
                product_orderRepository.save(productOrderEntity);
                return true;

            } catch (Exception e){
                System.out.printf("Error: " + e);
                return false;
            }
        }

        return false;
    }

    @Override
    public List<ProductOrderResponse> getAllProductOrder() {
        List<ProductOrderEntity> productOrderEntities = product_orderRepository.findAll();
        List<ProductOrderResponse> product_orderResponses = new ArrayList<>();

        for (ProductOrderEntity po : productOrderEntities) {
            ProductOrderResponse productOrderResponse = new ProductOrderResponse();
            productOrderResponse.setId_order(po.getKeys().getOrder().getId());
            productOrderResponse.setProductName(po.getKeys().getProduct().getName());
            productOrderResponse.setQuanity(po.getQuanity());
            productOrderResponse.setPrice(po.getPrice());
            productOrderResponse.setCreateDate(po.getCreateDate());

            product_orderResponses.add(productOrderResponse);
        }

        return product_orderResponses;
    }


    @Override
    public List<ProductOrderResponse> getProductOrderById(int id) {
        return null;
    }

    @Override
    public List<ProductOrderResponse> updateProductOrder(int id, String productName, int id_order, int quanity, BigDecimal price) {
        return null;
    }

    @Override
    public boolean deleteProductOrderById(int id) {
        return false;
    }
}
