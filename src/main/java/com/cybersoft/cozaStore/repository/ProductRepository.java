package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.ProductEntity;
import com.cybersoft.cozaStore.payload.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    List<ProductEntity> findByNameContaining(String productName);

    boolean existsByName(String name);

    @Query("SELECT p FROM product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(p.price AS string) LIKE CONCAT('%', :keyword, '%')")
    List<ProductEntity> searchProducts(@Param("keyword") String keyword);

}
