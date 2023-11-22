package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Integer> {

    Optional<CartEntity> findByUserIdAndProductId(@Param("id_user") int userId, @Param("id_product") int productId);

    List<CartEntity> findByUserId(int idUser);
}
