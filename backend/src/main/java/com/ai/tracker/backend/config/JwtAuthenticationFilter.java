package com.ai.tracker.backend.config;

import com.ai.tracker.backend.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        String method = request.getMethod();

        // Bypass public endpoints
        if (path.equals("/api/auth/login") || path.equals("/api/auth/register") ||
                (path.equals("/api/records") && method.equals("POST"))) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        log.info("Request {} {} - Authorization header: {}", method, path, authHeader);

        String username = null;
        String jwtToken = null;

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwtToken = authHeader.substring(7).trim();
                log.info("Extracted token (first 20 chars): {}", (jwtToken.length() > 20 ? jwtToken.substring(0,20) + "..." : jwtToken));
                username = jwtUtil.extractUsername(jwtToken);
                log.info("Username from token: {}", username);
            } else {
                log.info("No Bearer token found in Authorization header.");
            }
        } catch (Exception e) {
            log.error("Error while extracting username from token: {}", e.getMessage(), e);
        }

        try {
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.info("Loading user details for '{}'", username);
                UserDetails userDetails = userService.loadUserByUsername(username);
                log.info("UserDetails loaded: username={}, authorities={}", userDetails.getUsername(), userDetails.getAuthorities());

                boolean valid = false;
                try {
                    valid = jwtUtil.validateToken(jwtToken, userDetails);
                } catch (Exception e) {
                    log.error("Error during token validation: {}", e.getMessage(), e);
                }
                log.info("Token validation result for {}: {}", username, valid);

                if (valid) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("Authentication set in SecurityContext for user: {}", username);
                } else {
                    log.info("Token invalid or not matching user details.");
                }
            }
        } catch (Exception e) {
            log.error("Unexpected error in JwtAuthenticationFilter: {}", e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }
}
