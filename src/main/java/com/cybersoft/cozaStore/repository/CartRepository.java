package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Integer> {

}
