package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.api.ApiLoginController;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.response.UserResponse;
import com.cybersoft.cozaStore.service.imp.LoginServiceImp;
import com.cybersoft.cozaStore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private ApiLoginController apiLoginController;

    @Autowired
    private UserServiceImp userServiceImp;


    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        if (!userServiceImp.checkEmail(email)) {
            return "login";
        }

        UserEntity user = userServiceImp.findByEmail(email);

        if (user != null && userServiceImp.checkPassword(email, password)) {
            if ("ADMIN".equals(user.getRole())) {
                return "adminHome";
            } else {
                return "home-02";
            }
        }

        return "redirect:/login?error=password";
    }

}
