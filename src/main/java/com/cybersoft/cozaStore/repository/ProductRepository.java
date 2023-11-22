package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    List<ProductEntity> findByNameContaining(String productName);

    boolean existsByName(String name);

    @Query("SELECT p FROM product p WHERE p.name LIKE %:query% OR p.description LIKE %:query% OR CAST(p.price AS string) LIKE %:query%")
    List<ProductEntity> searchProducts(@Param("query") String query);



}
