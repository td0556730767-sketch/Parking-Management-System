package com.example.parking.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("DEBUG: Incoming request to: " + request.getRequestURI());
        String path = request.getRequestURI();

        // החרגה של נתיבי פתוחים (כמו לוגין ורגיסטר) כדי שהפילטר לא יציק להם
        if (path.startsWith("/auth") || path.contains("/login") || path.contains("/register")
                || path.startsWith("/api/transactions/start") || path.startsWith("/api/transactions/exit")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = jwtUtil.extractClaims(token);
                String username = claims.getSubject();

                 String role = claims.get("role", String.class);
                List<GrantedAuthority> authorities = role == null
                        ? List.of()
                        : List.of(new SimpleGrantedAuthority("ROLE_" + role));

                // יוצרים את אובייקט ה-Auth הרשמי של Spring
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                // 🔥 התיקון כאן: רושמים רשמית בזיכרון של Spring Security שהמשתמש מאומת!
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("DEBUG: Token is VALID and user is registered in SecurityContext ✅");

            } catch (JwtException e) {
                System.out.println("DEBUG: Token validation FAILED ❌");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        // ממשיכים הלאה בשרשרת הפילטרים אל הקונטרולר
        filterChain.doFilter(request, response);
    }
}