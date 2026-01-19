package com.example.kiranastore.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

public class RateLimitingFilter extends OncePerRequestFilter {

    private final StringRedisTemplate redisTemplate;

    public RateLimitingFilter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        /* ===============================
            Skip auth endpoints
           =============================== */
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        /* ===============================
            SUPER_ADMIN → unlimited
           =============================== */
        if (auth != null && auth.isAuthenticated()) {
            for (GrantedAuthority authority : auth.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_SUPER_ADMIN")) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }

        /* ===============================
           ⃣ Determine rate limit
           =============================== */
        int limit;
        int windowSeconds = 60;

        if (path.startsWith("/admin")) {
            limit = 3;
        } else if (path.startsWith("/transactions")) {
            limit = 20;
        } else if (path.startsWith("/reports")) {
            limit = 10;
        } else {
            limit = 10;
        }

        /* ===============================
           4⃣ Build Redis key
           =============================== */
        String key;

        if (auth != null && auth.isAuthenticated()) {
            // userId comes from JWT (principal)
            key = "rate:user:" + auth.getName() + ":" + path;
        } else {
            key = "rate:ip:" + request.getRemoteAddr() + ":" + path;
        }

        /* ===============================
           5⃣ Redis increment
           =============================== */
        Long count = redisTemplate.opsForValue().increment(key);

        if (count != null && count == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(windowSeconds));
        }

        /* ===============================
           6️ Proof logs
           =============================== */
        System.out.println(
                "⏱ RATE LIMIT | key=" + key +
                        " count=" + count +
                        " limit=" + limit
        );

        if (count != null && count > limit) {
            response.setStatus(429);
            response.getWriter().write("Too many requests");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
