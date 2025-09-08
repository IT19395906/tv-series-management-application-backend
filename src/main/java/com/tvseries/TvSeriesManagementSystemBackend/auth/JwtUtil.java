package com.tvseries.TvSeriesManagementSystemBackend.auth;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final String SECRET = "";
    private final long EXPIRATION_TIME = 1000 * 60 * 60;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public String extractUserName(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token, String username) {
       try{
         return extractUserName(token).equals(username) && !isTokenExpired(token);
       }
       catch(SecurityException e){
        logger.error("Invalid JWT signature: {}", e.getMessage());
       }
       catch(MalformedJwtException e){
        logger.error("Invalid jwt token: {}", e.getMessage());
       }
       catch(ExpiredJwtException e){
        logger.error("Jwt token is expired: {}", e.getMessage());
       }
       catch(UnsupportedJwtException e){
        logger.error("Jwt token is unsupport: {}", e.getMessage());
       }
       catch(IllegalArgumentException e){
        logger.error("Jwt claims string is empty: {}", e.getMessage());
       }
       return false;
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
