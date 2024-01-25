package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.CategoryEntity;
import com.cybersoft.cozaStore.entity.ProductEntity;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    boolean existsByName(String name);


    @Query("SELECT c FROM category c WHERE c.name LIKE %:query%")
    List<CategoryEntity> searchCategories(@Param("query") String query);

    List<CategoryEntity> findTop5ByOrderByCreateDateDesc();

    CategoryEntity findByName(String categoryName);
}
