package com.example.parking.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // יצירת מפתח סודי קבוע ובטוח באורך המתאים ל-HS256
    private final String SECRET_KEY = "mySuperSecretKeyForParkingSystemProject2026!";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email, String password, long userId, String role) {
        return Jwts.builder()
                .subject(email) // בגרסה החדשה: subject במקום setSubject
                .claim("password", password)
                .claim("userId", userId) // מוודא שה-userId באמת נכנס לטוקן
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // שעה אחת
                .signWith(key) // בגרסה החדשה לא צריך לציין את ה-Algorithm, הוא מזהה לבד מהמפתח
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser() // בגרסה החדשה: parser במקום parserBuilder
                .verifyWith(key) // בגרסה החדשה: verifyWith במקום setSigningKey
                .build()
                .parseSignedClaims(token) // בגרסה החדשה: parseSignedClaims במקום parseClaimsJws
                .getPayload(); // בגרסה החדשה: getPayload במקום getBody
    }

    public String getUserEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public String getUserpassword(String token) {
        // תיקון: שולף את ה-claim של הסיסמה ולא את ה-subject (האימייל)
        return extractClaims(token).get("password", String.class);
    }

    public Long getUserId(String token) {
        return extractClaims(token).get("userId", Long.class);
    }
}