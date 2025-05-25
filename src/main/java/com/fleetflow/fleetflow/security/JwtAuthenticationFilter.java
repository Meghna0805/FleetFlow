package com.fleetflow.fleetflow.security;

import com.fleetflow.fleetflow.service.CustomUserDetailsService;
import com.fleetflow.fleetflow.service.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                System.out.println(" JWT Username extracted: " + username);
            } catch (Exception e) {
                System.out.println(" Error parsing JWT: " + e.getMessage());
                filterChain.doFilter(request, response);
                return;
            }
        } else {
            System.out.println("⚠️ Authorization header missing or not Bearer.");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("👤 Loaded user from DB: " + userDetails.getUsername());

            if (jwtUtil.validateToken(jwt, userDetails)) {
                List<String> roles = jwtUtil.extractRoles(jwt);
                System.out.println("🔐 Extracted roles from JWT: " + roles);

                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                System.out.println("✅ Granted authorities set: " + authorities);

                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("✅ SecurityContext updated with user: " + username);
            } else {
                System.out.println("⛔ JWT token invalid for user: " + username);
            }
        } else if (SecurityContextHolder.getContext().getAuthentication() != null) {
            System.out.println("⚠️ Security context already has authentication.");
        }

        filterChain.doFilter(request, response);
    }
}
