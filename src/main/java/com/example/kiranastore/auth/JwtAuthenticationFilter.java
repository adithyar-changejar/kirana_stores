package com.example.kiranastore.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("---- JWT FILTER START ----");
        System.out.println("URI = " + request.getRequestURI());

        String header = request.getHeader("Authorization");
        System.out.println("Authorization Header = " + header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            System.out.println("Token extracted");

            try {
                boolean valid = jwtTokenProvider.validateToken(token);
                System.out.println("Token valid = " + valid);

                String userId = jwtTokenProvider.getUserId(token);
                String role = jwtTokenProvider.getRole(token);

                System.out.println("JWT userId = " + userId);
                System.out.println("JWT role = " + role);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("Authentication set in SecurityContext");
                System.out.println("Authorities = " + authentication.getAuthorities());

            } catch (Exception e) {
                System.out.println("JWT ERROR ❌");
                e.printStackTrace();
            }
        } else {
            System.out.println("No Bearer token");
        }

        filterChain.doFilter(request, response);

        System.out.println("---- JWT FILTER END ----");
    }
}
