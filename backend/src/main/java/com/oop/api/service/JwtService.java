package com.oop.api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.oop.api.repository.TokenRepository;

/**
 * This class provides JWT (JSON Web Token) related functionality.
 * It allows for generating and validating JWT tokens, extracting claims from tokens,
 * and checking token expiration.
 */

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    private final TokenRepository tokenRepository;

    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a claim from the given JWT token using the provided claims resolver function.
     *
     * @param <T> the type of the claim to extract
     * @param token the JWT token from which to extract the claim
     * @param claimsResolver the function that resolves the claim from the JWT token's claims
     * @return the extracted claim
     */

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Builds a JWT token with the provided extra claims, user details, and expiration time.
     *
     * @param extraClaims   the extra claims to include in the token
     * @param userDetails  the user details used to set the subject of the token
     * @param expiration    the expiration time of the token in milliseconds
     * @return the generated JWT token
     */

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Checks if the provided token is valid for the given user.
     *
     * @param token The token to be validated.
     * @param user The user details for whom the token is being validated.
     * @return {@code true} if the token is valid for the user, {@code false} otherwise.
     */

    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUsername(token);

        boolean validToken = tokenRepository
            .findByToken(token)
            .map(t -> !t.isLoggedOut())
            .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Represents the claims contained within a JSON Web Token (JWT).
     * Claims are statements about an entity (typically, the user) and additional metadata.
     * Claims are represented as key-value pairs.
     */
    
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the signing key used for JWT token generation.
     *
     * @return The signing key as a Key object.
     */

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
