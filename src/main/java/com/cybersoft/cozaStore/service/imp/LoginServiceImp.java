package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.payload.response.UserResponse;

import java.util.List;

public interface LoginServiceImp {
    boolean insertUser(SignUpRequest signUpRequest );

    List<UserResponse> getAllUser();

    List<UserResponse> getUserById(int id);

    List<UserResponse> searchUsers(String query);

    boolean deleteUserById(int id);
}
