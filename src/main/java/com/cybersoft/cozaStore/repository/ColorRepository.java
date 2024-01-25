package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, Integer> {
    ColorEntity findByName(String colorName);
}
