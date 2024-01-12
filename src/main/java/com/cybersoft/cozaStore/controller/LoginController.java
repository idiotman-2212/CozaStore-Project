/*
package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.api.ApiLoginController;
import com.cybersoft.cozaStore.entity.RoleEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.payload.response.UserResponse;
import com.cybersoft.cozaStore.service.imp.LoginServiceImp;
import com.cybersoft.cozaStore.service.imp.UserServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/login")
public class LoginController {

    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping("/signin")
    public String loginPage(Model model, @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "login";
    }

    @PostMapping("/signin")
    public String signup(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String repeatPassword,
            @RequestParam(required = false) String emailAddress,
            Model model
    ) {
        // Kiểm tra xem action là login hay signup
        if (username == null && repeatPassword == null && emailAddress == null) {
            // Đây là phần xử lý đăng nhập
            if (!userServiceImp.checkEmail(email)) {
                model.addAttribute("error", true);
                return "login";
            }

            UserEntity user = userServiceImp.findByEmail(email);

            if (user != null && userServiceImp.checkPassword(email, password)) {
                if ("ADMIN".equals(user.getRole())) {
                    return "adminHome";
                } else {
                    return "index";
                }
            }

            model.addAttribute("error", true);
            return "login";
        }
        return "login";
    }

    @GetMapping("/signup")
    public String registration(Model model) {
        if (!isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("userForm", new UserEntity());

        return "login";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }
}
*/
// src/main/java/com/example/controller/LoginController.java
package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
   // @Lazy
    private PasswordEncoder passwordEncoder;
        @GetMapping("/signin")
        public String showLogin() {
            return "login";
        }

        //Check for Credentials
        @PostMapping("/signin")
        public String login(@ModelAttribute(name="loginForm")SignUpRequest signUpRequest, Model m) {

            UserEntity user = userRepository.findByEmail(signUpRequest.getUserName());

            if (user != null && passwordEncoder.matches(signUpRequest.getPassword(), user.getPassword())) {
                // Đăng nhập thành công
                m.addAttribute("user", signUpRequest.getUserName());
                m.addAttribute("pass", signUpRequest.getPassword());
                return "index";
            } else {
                // Sai thông tin đăng nhập
                m.addAttribute("error", "Incorrect Username & Password");
                return "login";
            }
        }
}

