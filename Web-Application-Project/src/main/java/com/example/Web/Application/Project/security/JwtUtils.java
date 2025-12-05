package com.example.Web.Application.Project.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.Web.Application.Project.domain.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUtils {

    private static final long EXPIRATION_TIME_IN_MILISECOND = 1000L * 60L * 60L * 24L * 30L * 6L; //expires in 6 months

    private SecretKey key ;

    @Value("${secreteJwtString}")
    private String secreteJwtString;

    @PostConstruct
    private void init(){
        byte[] keyBytes= secreteJwtString.getBytes(StandardCharsets.UTF_8);
        this.key = new SecretKeySpec(keyBytes , "HmacSHA256");
    }

    public String generateToken( User user ){
         String username = user.getEmail();
         return generateToken(username);
    }

    public String generateToken(String username){

        return Jwts.builder()
               .subject(username)
               .issuedAt(new Date(System.currentTimeMillis()))
               .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MILISECOND))
               .signWith(key)
               .compact();


    }

    public String getUsernameFromToken(String token ){
        return extractClaims(token , Claims::getSubject);
    }

    private <T> T extractClaims(String token , Function<Claims , T> claimsFunction){
        return claimsFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration ).before(new Date());
    }
}
