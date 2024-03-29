package com.mariojunior.todo.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKeyBySecret())
                .compact();
    }

    public boolean isValidToken(String token){
        Claims claims = getClaims(token);
        if(Objects.nonNull(claims)){
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            return Objects.nonNull(username) && Objects.nonNull(expirationDate) && now.before(expirationDate);
        }
        return false;
    }

    private Claims getClaims(String token) { //Claims = validar o token com a secret key
        SecretKey secretKey = getKeyBySecret();
        try{
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (Exception e){
            return null;
        }
    }

    public String getUsername(String token){
        Claims claims = getClaims(token);
        if(Objects.nonNull(claims)){
            return claims.getSubject();
        }
        return null;
    }

    private SecretKey getKeyBySecret(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
