package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.payload.response.UserResponse;
import com.cybersoft.cozaStore.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class RegistrationController {
    @Autowired
    private LoginServiceImp loginServiceImp;

    @ModelAttribute("user")
    public SignUpRequest signUpRequest(){
        return new SignUpRequest();
    }

    @GetMapping("")
    public String showSignUpForm(Model model){
        model.addAttribute("user", new SignUpRequest());
        return "signup";
    }

    @PostMapping("")
    public String registration(@ModelAttribute("user")SignUpRequest signUpRequest){
        loginServiceImp.insertUser(signUpRequest);
        return "redirect:/signup?success";
    }
}
