package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, Integer> {

}
