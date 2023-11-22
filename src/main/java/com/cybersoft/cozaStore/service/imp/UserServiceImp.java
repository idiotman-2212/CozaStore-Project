package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.response.UserResponse;

import java.util.List;

public interface UserServiceImp {
    UserEntity findByEmail(String email);

    //void createPasswordResetTokenForUser(UserEntity user, String token);


}
