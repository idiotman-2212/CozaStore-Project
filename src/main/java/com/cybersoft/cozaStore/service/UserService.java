package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.PasswordResetTokenEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
//import com.cybersoft.cozaStore.repository.PasswordResetTokenRepository;
import com.cybersoft.cozaStore.payload.response.UserResponse;
import com.cybersoft.cozaStore.repository.UserRepository;
import com.cybersoft.cozaStore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordResetTokenRepository passwordTokenRepository;
    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



//    @Override
//    public void createPasswordResetTokenForUser(UserEntity user, String token) {
//        PasswordResetTokenEntity myToken = new PasswordResetTokenEntity(token, user);
//        passwordTokenRepository.save(myToken);
//    }

//    @Override
//    public void updatePassword(String password, Long userId) {
//        userRepository.updatePassword(password, userId);
//    }
}
