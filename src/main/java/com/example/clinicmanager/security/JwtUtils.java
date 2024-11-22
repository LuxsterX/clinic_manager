package com.example.clinicmanager.security;

import io.jsonwebtoken.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for JWT operations, including token generation, parsing, and validation.
 */
@Component
@Tag(name = "JWT Utilities", description = "Utilities for handling JWT tokens such as generation, validation, and extraction of details.")
public class JwtUtils {

    @Schema(description = "Secret key used for signing the JWT tokens", example = "secretKey")
    private final String jwtSecret = "secretKey";

    @Schema(description = "Expiration time for the JWT tokens in milliseconds", example = "1800000")
    private final long jwtExpirationMs = 1800000;

    /**
     * Generates a JWT token for a given username.
     *
     * @param username the username for which the token is generated
     * @return a signed JWT token
     */
    @Operation(summary = "Generate JWT Token", description = "Generates a JWT token for a given username.")
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Extracts the username from a given JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    @Operation(summary = "Extract Username from Token", description = "Extracts the username from the provided JWT token.")
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Validates a given JWT token for expiration and correctness.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    @Operation(summary = "Validate JWT Token", description = "Validates the provided JWT token for correctness and expiration.")
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
