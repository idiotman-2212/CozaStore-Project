package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.ProductOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Product_OrderRepository extends JpaRepository<ProductOrderEntity, Integer> {

}
