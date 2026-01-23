package com.example.kiranastore.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * The type Jwt token provider.
 */
@Component
public class JwtTokenProvider {

    //   32 chars for HS256
    private static final String SECRET =
            "ronaldo-is-the-goat-and-manchester-united-is-my-club";
            //"kiranastore-secret-key-very-secure-256bit";

    private static final long EXPIRY_MS = 60 * 60 * 1000; // 1 hour

    /**
     * Generate token string.
     *
     * @param userId the user id
     * @param role   the role
     * @return the string
     */
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

    /**
     * Gets role.
     *
     * @param token the token
     * @return the role
     */
    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }


    /**
     * Gets user id.
     *
     * @param token the token
     * @return the user id
     */
    public String getUserId(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Validate token boolean.
     *
     * @param token the token
     * @return the boolean
     */
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

    /**
     * Gets expiry.
     *
     * @return the expiry
     */
    public long getExpiry() {
        return EXPIRY_MS / 1000; // seconds
    }
}
