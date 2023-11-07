package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.PasswordResetTokenEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.repository.PasswordResetTokenRepository;
import com.cybersoft.cozaStore.service.imp.ResetPasswordServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class ResetPasswordService implements ResetPasswordServiceImp {
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }


    public LocalDateTime expireTimeRange(){
        return LocalDateTime.now().plusMinutes(10);
    }

    public void sendEmail(String to,String subject, String emailLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emaiContent = "<p> Hello </p>"
                            + "Click the link below to reset password"
                            + "<p><a href\"" + emailLink + "\">Change My Password</a></p>"
                            + "<br>"
                            + "Ignore This Email if you not made the request";
        helper.setText(emaiContent,true);
        helper.setFrom("abclsdjf23@gmail.com", "Chau Huy Dien");
        helper.setSubject(subject);
        helper.setTo(to);

        javaMailSender.send(message);
    }
}
