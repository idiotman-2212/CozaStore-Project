package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.payload.response.UserResponse;

import java.util.List;

public interface UserServiceImp {
    UserEntity findByEmail(String email);

    //void createPasswordResetTokenForUser(UserEntity user, String token);
    boolean checkPassword(String email, String password);
<<<<<<< HEAD

    boolean checkEmail(String email);

    boolean insertUser(SignUpRequest signUpRequest );

    List<UserResponse> getAllUser();

    List<UserResponse> getUserById(int id);

    List<UserResponse> searchUsers(String query);

    boolean deleteUserById(int id);

=======
>>>>>>> idiotman-2212-patch-2

    boolean checkEmail(String email);

    boolean insertUser(SignUpRequest signUpRequest );

    List<UserResponse> getAllUser();

    List<UserResponse> getUserById(int id);

    List<UserResponse> searchUsers(String query);

    boolean deleteUserById(int id);

}
