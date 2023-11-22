package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
    boolean existsByTitle(String title);
    boolean existsByContent(String content);
}
