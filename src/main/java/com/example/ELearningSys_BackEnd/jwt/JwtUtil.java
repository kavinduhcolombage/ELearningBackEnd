package com.example.ELearningSys_BackEnd.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "mysecretkey";

    public String generateToken(String email){
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

    public String extractEmail(String tocken){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(tocken).getBody().getSubject();
    }

    public boolean isTokenExpired(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String email){
        return email.equals(extractEmail(token)) && !isTokenExpired(token);
    }

    
}
