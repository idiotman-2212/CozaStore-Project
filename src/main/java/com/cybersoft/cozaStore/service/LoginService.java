package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.ProductEntity;
import com.cybersoft.cozaStore.entity.RoleEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.payload.response.ProductResponse;
import com.cybersoft.cozaStore.payload.response.UserResponse;
import com.cybersoft.cozaStore.repository.RoleRepository;
import com.cybersoft.cozaStore.repository.UserRepository;
import com.cybersoft.cozaStore.service.imp.LoginServiceImp;
import com.cybersoft.cozaStore.service.imp.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LoginService implements LoginServiceImp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleServiceImp roleServiceImp;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean insertUser(SignUpRequest signUpRequest) {
        // Check if the user already exists
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            System.out.println("Email đã tồn tại");
            return false; // Registration failed, user already exists
        }

        // Create a new user entity
        UserEntity newUser = new UserEntity();
        newUser.setUsername(signUpRequest.getUserName());
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        // Set default role for the user
        RoleEntity defaultRole = roleServiceImp.getRoleById(signUpRequest.getIdRole());
        newUser.setRole(defaultRole);

        // Save the user entity
        userRepository.save(newUser);

        return true; // Registration successful
    }

    public boolean checkLogin(String username, String password){
        List<UserEntity> list = userRepository.findByUsernameAndPassword(username,password);
        return list.size() > 0;
    }
    @Override
    public List<UserResponse> getAllUser() {

        List<UserEntity> list = userRepository.findAll();
        List<UserResponse> responseList = new ArrayList<>();
        for (UserEntity u: list) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(u.getId());
            userResponse.setUsername(u.getUsername());
            userResponse.setEmail(u.getEmail());
            userResponse.setCreateDate(u.getCreateDate());

            responseList.add(userResponse);
        }

        return responseList;
    }

    @Override
    public List<UserResponse> getUserById(int id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);

        // Kiểm tra xem productOptional có giá trị tồn tại không
        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();
            UserResponse userResponse = new UserResponse();
            userResponse.setId(userEntity.getId());
            userResponse.setUsername(userEntity.getUsername());
            userResponse.setEmail(userEntity.getEmail());
            userResponse.setCreateDate(userEntity.getCreateDate());

            List<UserResponse> userResponses = new ArrayList<>();
            userResponses.add(userResponse);

            return userResponses;
        } else {
            // Trả về danh sách rỗng nếu không tìm thấy sản phẩm
            return Collections.emptyList();
        }
    }


    @Override
    public List<UserResponse> searchUsers(String query) {

        List<UserEntity> list = userRepository.searchUsers(query);
        List<UserResponse> responseList = new ArrayList<>();
        for (UserEntity u: list) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(u.getId());
            userResponse.setUsername(u.getUsername());
            userResponse.setEmail(u.getEmail());
            userResponse.setCreateDate(u.getCreateDate());

            responseList.add(userResponse);
        }

        return responseList;
    }

    @Override
    public boolean deleteUserById(int id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }


}
