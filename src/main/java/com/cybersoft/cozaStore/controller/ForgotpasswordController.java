package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.entity.PasswordResetTokenEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.response.UserResponse;
import com.cybersoft.cozaStore.repository.PasswordResetTokenRepository;
import com.cybersoft.cozaStore.repository.UserRepository;
import com.cybersoft.cozaStore.service.EmailService;
import com.cybersoft.cozaStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.UUID;

@Controller
public class ForgotpasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/forgotpassword")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("userDTO", new UserResponse()); // Thêm đối tượng UserResponse vào model
        return "forgotpassword";
    }

    @PostMapping("/forgotpassword")
    public String processForgotPasswordForm(@ModelAttribute("userDTO") UserResponse userDTO, Model model) {
        String email = userDTO.getEmail(); // Lấy giá trị email từ đối tượng UserResponse

        UserEntity user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("error", "Email not found");
            return "forgotpassword";
        }

        String token = UUID.randomUUID().toString();
        PasswordResetTokenEntity passwordResetToken = new PasswordResetTokenEntity(token, user);
        passwordResetToken.setExpiryDate(new Date(System.currentTimeMillis() + PasswordResetTokenEntity.EXPIRATION_MINUTES * 60 * 1000));
        tokenRepository.save(passwordResetToken);

        String resetLink = "http://localhost:8080/resetPassword?token=" + token; // Thay thế bằng đường dẫn của ứng dụng thực tế

        // Gửi email với đường link reset mật khẩu
        String emailSubject = "Password Reset Request";
        String emailBody = "Click the link below to reset your password:\n" + resetLink;
        emailService.sendResetPasswordEmail(email, emailSubject, emailBody);

        model.addAttribute("successMessage", "Password reset link sent to your email address");
        return "forgotpassword";
    }
}
