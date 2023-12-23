package edu.uoc.workoutwizardusermanagement.configuration;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class JwtSecretKey {

    private final SecretKey secretKey;

    public JwtSecretKey() {
        final var key = generateKey();
        System.out.println("In case you need  another key = " + key);
        byte[] decodedKey = Base64.getDecoder().decode(System.getenv("JWT_USER_MS_KEY"));
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public static String generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
            keyGen.init(512, new SecureRandom());
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating key", e);
        }
    }
}
