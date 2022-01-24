package com.example.gradlebnbadminapi.jwt;

import com.example.gradlebnbadminapi.exception.UnauthenticatedException;
import com.example.gradlebnbadminapi.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TokenProvider {

    private final Key key;

    public TokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(User user) {

        long curTime = System.currentTimeMillis();

        return Jwts.builder()
                .claim("email", user.getEmail())
                .claim("access", user.getAccess().getId())
                .claim("status", user.getStatus())
                .setExpiration(new Date(curTime + 60 * 60 * 24 * 1000))
                .setIssuedAt(new Date(curTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("JWT TOKEN ERROR: {}", e.getMessage());
            return null;
        }
    }

    public Map<String, Object> getClaimsData(Authentication authentication) {

        Claims claims = null;
        if (authentication != null && authentication.getPrincipal() != null) {
            claims = (Claims) authentication.getPrincipal();
        } else {
            throw new UnauthenticatedException();
        }

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("email", claims.get("email", String.class));
        map.put("access", claims.get("access", Integer.class));
        map.put("status", claims.get("status", String.class));

        return map;
    }

//    public void getAuthPermission(Long id, Authentication authentication) {
//
//        if(authentication == null) {
//            throw new UnauthenticatedException();
//        }
//        String userEmail = userRepository.findById(id).orElseThrow(NoSuchElementException::new).getUserEmail();
//
//        if(!userEmail.equals(getClaimsData(authentication).get("userEmail").toString())) {
//            throw new UnauthenticatedException();
//        }
//    }
//
//    public void getAccessAllPermission(Authentication authentication) {
//        if(authentication == null || !List.of(8,9).contains(getClaimsData(authentication).get("access"))) {
//            throw new UnauthenticatedException();
//        };
//    }
//
//    public void getAccessAdminPermission(Authentication authentication) {
//        if(!Objects.equals(9, getClaimsData(authentication).get("access"))) {
//            throw new UnauthenticatedException();
//        }
//    }
}
