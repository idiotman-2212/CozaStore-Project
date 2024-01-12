package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.PasswordResetTokenEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.repository.PasswordResetTokenRepository;
import com.cybersoft.cozaStore.service.imp.ISecurityUserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Calendar;

public class ISecurityUserService implements ISecurityUserServiceImp {
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Override
    public String validatePasswordResetToken(long id, String token) {
        final PasswordResetTokenEntity passToken = passwordTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getId() != id)) {
            return "invalidToken";
        }

        final Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "expired";
        }
/// VALID USER LOGIN LUON =))
        final UserEntity user = passToken.getUser();
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
}

