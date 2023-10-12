package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoty extends JpaRepository<ProductEntity, Integer> {

}
