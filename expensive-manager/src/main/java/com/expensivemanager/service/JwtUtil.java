package com.expensivemanager.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for handling JWT token creation, validation, and claims extraction.
 * Centralizes all JWT logic for authentication and authorization.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;

    /**
     * Gets the SecretKey instance used for signing and parsing JWTs.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

      /**
     * Generates a JWT token with custom claims.
     * @param username The username to set as subject.
     * @param extraClaims Additional claims (e.g., roles, userId).
     * @return The signed JWT token string.
     */
    public String generateToken(String username, Map<String, Object> extraClaims) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        JwtBuilder builder = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512);

        return builder.compact();
    }

     /**
     * Generates a JWT token with no additional claims.
     * @param username The username for the token subject.
     * @return The JWT token.
     */
    public String generateToken(String username) {
        return generateToken(username, Map.of());
    }

     /**
     * Generates a JWT token for a UserDetails object (adds roles as claims).
     * @param userDetails The UserDetails (from Spring Security).
     * @return The JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = Map.of("roles", userDetails.getAuthorities());
        return generateToken(userDetails.getUsername(), claims);
    }


    /**
     * Extracts the username (subject) from a JWT token.
     * @param token JWT token string.
     * @return Username embedded in token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

     /**
     * Extracts roles claim from JWT, if present.
     * @param token JWT token.
     * @return Roles as string, or null if not found.
     */
    public String extractRoles(String token) {
        final Claims claims = extractAllClaims(token);
        Object roles = claims.get("roles");
        return roles != null ? roles.toString() : null;
    }

     /**
     * Extracts any custom claim from the JWT.
     * @param token JWT token.
     * @param claimsResolver Function to resolve the claim.
     * @param <T> Type of the claim.
     * @return The claim value.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

     /**
     * Extracts all claims from a token. Throws exception if token is invalid.
     * @param token JWT token.
     * @return Claims object.
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

     /**
     * Validates the JWT token for username and expiration.
     * @param token JWT token.
     * @param username Username to compare.
     * @return True if valid and not expired.
     */
    public boolean validateToken(String token, String username) {
        try {
            final String tokenUsername = extractUsername(token);
            return (tokenUsername.equals(username) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

     /**
     * Validates the JWT token's structure and expiry.
     * @param token JWT token.
     * @return True if valid.
     */
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the token is expired.
     * @param token JWT token.
     * @return True if expired.
     */
    public boolean isTokenExpired(String token) {
         try {
            final Date expiration = extractClaim(token, Claims::getExpiration);
            return expiration.before(new Date());
        } catch (RuntimeException e) {
            // Check if caused by expiration
            if (e.getCause() instanceof ExpiredJwtException) {
                return true;
            }
            throw e;
        }
    }
    
}