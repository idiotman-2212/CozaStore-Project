package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.PasswordResetTokenEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
//import com.cybersoft.cozaStore.repository.PasswordResetTokenRepository;
import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.payload.response.UserResponse;
import com.cybersoft.cozaStore.repository.UserRepository;
import com.cybersoft.cozaStore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //    @Autowired
//    private PasswordResetTokenRepository passwordTokenRepository;
    @Override
    public UserEntity findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    public boolean checkPassword(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean checkEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user != null;
    }


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
            System.out.println("Them that bai " + e.getLocalizedMessage());

            isSuccess = false;
        }

        return isSuccess;
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
