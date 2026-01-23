package com.example.kiranastore.security;

import com.example.kiranastore.service.RateLimitRule;
import com.example.kiranastore.service.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * The type Rate limit filter.
 */
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    /**
     * Instantiates a new Rate limit filter.
     *
     * @param rateLimitService the rate limit service
     */
    public RateLimitFilter(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();
        String key = resolveKey(request);

        if (!rateLimitService.isAllowed(key, path)) {
            RateLimitRule rule = rateLimitService.resolveRule(path);

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                  "error": "Rate limit exceeded",
                  "limit": %d,
                  "window": "%s"
                }
                """.formatted(rule.limit(), rule.window()));
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String resolveKey(HttpServletRequest request) {
        // user-based if available
        String user = request.getHeader("X-USER-ID");
        if (user != null && !user.isBlank()) {
            return "user:" + user;
        }
        return "ip:" + request.getRemoteAddr();
    }
}
