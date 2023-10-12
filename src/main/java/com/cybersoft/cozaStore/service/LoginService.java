package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.repository.UserRepository;
import com.cybersoft.cozaStore.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService implements LoginServiceImp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean insertUser(SignUpRequest signUpRequest) {
        boolean isSuccess = false;
        UserEntity user = new UserEntity();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setUsername(signUpRequest.getUserName());

        try {
            userRepository.save(user);
            isSuccess = true;
        } catch (Exception e) {
            System.out.printf("Them that bai " + e.getLocalizedMessage());
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean checkLogin(String username, String password){
        List<UserEntity> list = userRepository.findByUsernameAndPassword(username,password);
        return list.size() > 0;
    }
}
