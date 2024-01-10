package com.cybersoft.cozaStore.provider;

import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Component
public class CustomAuthenProvider implements AuthenticationProvider{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Logic xử lí đăng nhập
        String username = authentication.getName();
        Object credentials = authentication.getCredentials();

        if (credentials == null) {
            throw new BadCredentialsException("Credentials not provided");
        }

        String password = credentials.toString();

        // Kiểm tra xem người dùng có tồn tại hay không
        UserEntity user = userRepository.findByEmail(username);
        if (user != null) {
            // Kiểm tra mật khẩu
            if (password != null && passwordEncoder.matches(password, user.getPassword())) {
                // Tạo chứng thực --- GrantedAuthority một class chứng thực của SS
                List<GrantedAuthority> roles = new ArrayList<>();
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().getName());

                roles.add(grantedAuthority);

                // Tạo chứng thực cho security
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(username, user.getPassword(), roles);
                SecurityContextHolder.getContext().setAuthentication(token);

                return token;
            } else {
                throw new BadCredentialsException("Invalid password");
            }
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}