package com.cybersoft.cozaStore.repository;

import com.cybersoft.cozaStore.entity.ProductEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
    List<UserEntity> findByUsernameAndPassword(String username, String password);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("SELECT u FROM user u WHERE u.username LIKE %:query% OR u.email LIKE %:query%")
    List<UserEntity> searchUsers(@Param("query") String query);



}
