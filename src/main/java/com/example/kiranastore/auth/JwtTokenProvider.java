package com.example.kiranastore.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    //   32 chars for HS256
    private static final String SECRET =
            "ronaldo-is-the-goat-and-manchester-united-is-my-club";
            //"kiranastore-secret-key-very-secure-256bit";

    private static final long EXPIRY_MS = 60 * 60 * 1000; // 1 hour

    public String generateToken(String userId, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRY_MS);

        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(
                        Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }


    public String getUserId(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        parseClaims(token); // throws exception if invalid
        return true;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8))
                )
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public long getExpiry() {
        return EXPIRY_MS / 1000; // seconds
    }
}
