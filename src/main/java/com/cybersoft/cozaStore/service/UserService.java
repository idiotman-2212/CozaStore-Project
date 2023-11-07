package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.repository.UserRepository;
import com.cybersoft.cozaStore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updatePassword(String password, Long userId) {
        userRepository.updatePassword(password, userId);
    }
}
