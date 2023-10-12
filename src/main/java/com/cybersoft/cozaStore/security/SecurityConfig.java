package com.cybersoft.cozaStore.security;

import com.cybersoft.cozaStore.filter.JwtFilter;
import com.cybersoft.cozaStore.provider.CustomAuthenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Class sẽ được quét khi spring boot chạy ở tầng config
@EnableWebSecurity // Custom Spring Security


/**
 * Tạo danh sách user ảo lưu trên thanh ram dùng để chứng thực
 */

public class SecurityConfig {

    //TTạo ra chuẩn mã hoá password và lưu trên IOC
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    CustomAuthenProvider customAuthenProvider;

    //chỉ định authenticationManager sử dụng CustomAuthenProvider
    @Bean
    public AuthenticationManager authenticationManager (HttpSecurity httpSecurity) throws Exception {
        //CustomAuthenProvider customAuthenProvider = new CustomAuthenProvider();
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenProvider)
                .build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
//        //Tạo tài khoản có tên là admin có role là admin
//        UserDetails admin  = User.withUsername("admin").password(passwordEncoder.encode("123")).roles("ADMIN").build();
//
//        UserDetails user  = User.withUsername("user").password(passwordEncoder.encode("123")).roles("USER").build();
//
//        //Lưu trữ 2 user vừa tạo trên RAM
//        return new InMemoryUserDetailsManager(admin, user);

//    }

    //Cấu hình chứng thực cho các link địa chỉ cho Securit
    //GET/product: ai truy cập cũng được
    //PUT/product: ADMIN mới truy cập được
    //GET/cart: user mới truy cập được

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable() //Tắt tấn công theo kiểu cross-site(gọi link theo nhiều trình duyệt khác nhau)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //không cho sử dụng session
                .and()
                .authorizeHttpRequests()
                        .requestMatchers("/login/**").permitAll() //permitAll: là khong cần chứng thực
                        .requestMatchers("/file/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN")// link /product với phương thúc post phải có role ADMIN mới truy cập được
                        .requestMatchers(HttpMethod.GET, "/product").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/product").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/cart").hasRole("USER")
                        .anyRequest().authenticated() //Tất cả các link còn lại đều phải chứng thực
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
