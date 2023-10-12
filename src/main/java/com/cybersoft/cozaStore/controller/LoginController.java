package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.payload.response.Baseresponse;
import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.service.imp.LoginServiceImp;
import com.cybersoft.cozaStore.util.JwtHelper;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    private Gson gson = new Gson();

    @Autowired
    private LoginServiceImp loginServiceImp;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String email, @RequestParam String password){
        /*SecretKey key =  Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretString = Encoders.BASE64.encode(key.getEncoded());*/

        UsernamePasswordAuthenticationToken authen = new UsernamePasswordAuthenticationToken(email, password);

        //System.out.println("Kiem tra " + secretString);

        authenticationManager.authenticate(authen);

        //lấy danh sách role đã lưu trữ từ security context holder khi authemanager chứng thực thành công
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        String jsonRole = gson.toJson(roles);
//        for (SimpleGrantedAuthority data: roles) {
//            System.out.printf("role"  + data);
//        }
        String token = jwtHelper.generateToken(jsonRole);

        Baseresponse baseresponse = new Baseresponse();
        baseresponse.setStatusCode(200);
        baseresponse.setMessage("");
        baseresponse.setData(token);


        return new ResponseEntity<>(baseresponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest){
        boolean isSuccess = loginServiceImp.insertUser(signUpRequest);

        Baseresponse baseresponse = new Baseresponse();
        baseresponse.setStatusCode(200);
        baseresponse.setMessage("");
        baseresponse.setData(isSuccess);

        return new ResponseEntity<> (baseresponse, HttpStatus.OK);
    }
}
