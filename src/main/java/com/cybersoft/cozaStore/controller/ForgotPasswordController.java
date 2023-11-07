package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.service.imp.ResetPasswordServiceImp;
import com.cybersoft.cozaStore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private ResetPasswordServiceImp resetPasswordServiceImp;

    @GetMapping("")
    public String passwordRequest(){
        return null;
    }

}
