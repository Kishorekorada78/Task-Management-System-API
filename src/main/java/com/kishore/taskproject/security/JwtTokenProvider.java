package com.kishore.taskproject.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.kishore.taskproject.exception.ApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private static final String SECRET = "JWTSecretKeyJWTSecretKeyJWTSecretKey"; 
    // must be at least 32 characters for HS256+

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {

        String email = authentication.getName();

        Date now = new Date();
        Date expiry = new Date(now.getTime() + 3600000); // 1 hour

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getKey())
                .compact();
    }
    public String generateTokenFromEmail(String email) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + 604800000);
        System.out.println("Generating JWT for: " + email);
        System.out.println("Now = " + now);
        System.out.println("Expiry = " + expiry);
        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getKey())
                .compact();
    }
    public String getEmailFromToken(String token) {
    	 Date now = new Date();
         Date expiry = new Date(now.getTime() + 604800000);
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        System.out.println("Generating OAuth JWT for: " + token);
        System.out.println("Now = " + now);
        System.out.println("Expiry = " + expiry);
        return claims.getSubject();
    }
    
    public boolean validateToken(String token) {
        try {

            Claims claims = Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            System.out.println("TOKEN EMAIL = " + claims.getSubject());
            System.out.println("TOKEN EXPIRY = " + claims.getExpiration());

            return true;

        } catch (Exception e) {
            System.out.println("FAILED TOKEN = " + token);
            throw new ApiException("Token Issue: " + e.getMessage());
        }
    }
}