package edu.uoc.workoutwizardusermanagement.configuration;

import edu.uoc.workoutwizardusermanagement.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private final JwtSecretKey jwtSecretKey;

    public JwtTokenUtil() {
        this.jwtSecretKey = new JwtSecretKey();
    }

    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // Método para generar el token
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().claims(claims).subject(subject)
                .signWith(jwtSecretKey.getSecretKey())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24 hours duration
                .compact();
    }
}
