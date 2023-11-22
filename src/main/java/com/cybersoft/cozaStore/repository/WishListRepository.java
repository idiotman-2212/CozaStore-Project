package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishListEntity, Integer> {
    List<WishListEntity> findByUserId(int idUser);
}
