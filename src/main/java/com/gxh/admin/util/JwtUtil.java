package com.gxh.admin.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtUtil {

    private static final String SECRET_KEY = "store-admin-secret-key-2026";
    private static final long EXPIRATION_TIME = 60 * 60 * 1000 * 24;

    public static String generateToken(String userId, List<String> roleCodes) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("roleCodes", roleCodes);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean validateToken(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("userId", String.class);
    }

    @SuppressWarnings("unchecked")
    public static List<String> getRoleCodesFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("roleCodes", List.class);
    }
}