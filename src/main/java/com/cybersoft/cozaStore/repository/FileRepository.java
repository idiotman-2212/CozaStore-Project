package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository <FileEntity, Integer>{
    FileEntity findByName(String fileName);
}
