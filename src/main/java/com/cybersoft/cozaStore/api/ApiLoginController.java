package com.cybersoft.cozaStore.api;

import com.cybersoft.cozaStore.entity.RoleEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.payload.response.UserResponse;
import com.cybersoft.cozaStore.repository.RoleRepository;
import com.cybersoft.cozaStore.repository.UserRepository;
import com.cybersoft.cozaStore.service.imp.LoginServiceImp;
import com.cybersoft.cozaStore.util.JwtHelper;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class ApiLoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    private Gson gson = new Gson();

    @Autowired
    private LoginServiceImp loginServiceImp;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String email, @RequestParam String password){
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String secretString = Encoders.BASE64.encode(key.getEncoded());
//        System.out.printf("kiemtra " + secretString); // kiểm tra key

        UsernamePasswordAuthenticationToken authen = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(authen);

        // Lấy danh sách role dã lưu từ Security Context Holder khi AuthenManager chứng thực thành công
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) authentication.getAuthorities();

        String jsonRole = gson.toJson(roles);

        String token = jwtHelper.generateToken(jsonRole);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Đăng nhập thành công");
        baseResponse.setData(token);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUserName())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(signUpRequest.getUserName());
        userEntity.setEmail(signUpRequest.getEmail());
        userEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userEntity.setCreateDate(signUpRequest.getCreateDate());

        RoleEntity roleEntity = roleRepository.findByName("ROLE_ADMIN").orElse(null); // Sử dụng orElse để tránh null
        userEntity.setRole(roleEntity);

        userRepository.save(userEntity);

        // Trả về thông tin của người dùng sau khi đăng ký thành công
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Đăng kí thành công");
        baseResponse.setData(userEntity);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public String performLogout(HttpServletRequest request, HttpServletResponse response) {
        // Đăng xuất người dùng
        new SecurityContextLogoutHandler().logout(request, null, null);

        // Redirect đến trang đăng nhập hoặc trang chính
        return "redirect:/login.html"; // Thay thế bằng URL của trang đăng nhập của bạn
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUser(){
        List<UserResponse> userResponses = loginServiceImp.getAllUser();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Get all user");
        baseResponse.setData(userResponses);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public BaseResponse searchUsers(@RequestParam String query){
        BaseResponse response = new BaseResponse();
        List<UserResponse> userResponses = loginServiceImp.searchUsers(query);
        if(userResponses.isEmpty()){
            response.setMessage("No user found for the given query.");
        }else{
            response.setData(userResponses);
            response.setMessage("User found successfully.");
        }
        return response;
    }
}


