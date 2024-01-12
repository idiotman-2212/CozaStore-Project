package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.FileEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository <FileEntity, Integer>{
    FileEntity findByName(String fileName);

}
