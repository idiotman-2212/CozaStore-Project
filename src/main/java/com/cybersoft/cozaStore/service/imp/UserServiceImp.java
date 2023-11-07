package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.entity.UserEntity;

public interface UserServiceImp {
    UserEntity findByEmail(String email);
    void updatePassword(String password, Long userId);
}
