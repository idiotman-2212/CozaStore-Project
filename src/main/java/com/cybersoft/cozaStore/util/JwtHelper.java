package com.cybersoft.cozaStore.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtHelper {
    //@Value: giúp lấy thông tin cấu hình bên file application.properties
    @Value("${custom.token.key}")
    private  String secretKey;
    private long expiredTime = 8 * 60 * 60 * 1000;
    public String generateToken(String data){
        // Lấy key đã lưu trữ và sử dụng để tạo ra token
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        // Sinh ra thời gian hết hạn mới
        Date date = new Date();
        long newDateMilis = date.getTime() + expiredTime;
        Date newExpiredDate = new Date(newDateMilis);

        String token = Jwts.builder() // #1
                .setSubject(data)                    // #2
                .signWith(key)
                .setExpiration(newExpiredDate)
                .compact();
        return token;
    }
    /// Giai ma token
    public String parseToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        String data =  Jwts.parserBuilder()
                .setSigningKey(key).build()//truyền key cần giải mã token
                .parseClaimsJws(token)//Truyền vô token cần giải mã
                .getBody().getSubject();// lấy nội dung lưu trũ trong token
        return data;
    }
}
