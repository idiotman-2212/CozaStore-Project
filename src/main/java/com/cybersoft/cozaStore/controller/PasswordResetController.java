package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.entity.PasswordResetTokenEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.repository.PasswordResetTokenRepository;
import com.cybersoft.cozaStore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset-password")
public class PasswordResetController {

        @Autowired
        private UserServiceImp userService;
        @Autowired private PasswordResetTokenRepository tokenRepository;
        @Autowired private BCryptPasswordEncoder passwordEncoder;


        @GetMapping("")
        public String displayResetPasswordPage(@RequestParam(required = false) String token,
                                               UserEntity userEntity) {

            PasswordResetTokenEntity resetToken = tokenRepository.findByToken(token);
            if (resetToken == null){
                return null;
            } else if (resetToken.isExpired()){
                userEntity.addAttribute("error", "Token has expired, please request a new password reset.");
            } else {
                model.addAttribute("token", resetToken.getToken());
            }

            return "reset-password";
        }

        @PostMapping
        @Transactional
        public String handlePasswordReset(@ModelAttribute("passwordResetForm") @Valid PasswordResetDto form,
                                          BindingResult result,
                                          RedirectAttributes redirectAttributes) {

            if (result.hasErrors()){
                redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".passwordResetForm", result);
                redirectAttributes.addFlashAttribute("passwordResetForm", form);
                return "redirect:/reset-password?token=" + form.getToken();
            }

            PasswordResetToken token = tokenRepository.findByToken(form.getToken());
            User user = token.getUser();
            String updatedPassword = passwordEncoder.encode(form.getPassword());
            userService.updatePassword(updatedPassword, user.getId());
            tokenRepository.delete(token);

            return "redirect:/login?resetSuccess";
        }

    }
}
