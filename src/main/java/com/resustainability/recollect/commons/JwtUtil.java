package com.resustainability.recollect.commons;

import com.resustainability.recollect.exception.JwtException;
import com.resustainability.recollect.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final long DEFAULT_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 days

    private final String secretKey;
    private final String jwtClaimName;

    @Autowired
    public JwtUtil(
            @Value("${app.jwt.secretKey}") String secretKey,
            @Value("${app.jwt.claimName}") String jwtClaimName
    ) {
        this.secretKey = secretKey;
        this.jwtClaimName = jwtClaimName;
    }

    public String generateToken(String username, LocalDateTime tokenAt) {
        return generateToken(
                username,
                tokenAt,
                true
        );
    }

    public String generateToken(String username, LocalDateTime tokenAt, boolean expiry) {
        return generateToken(
                username,
                tokenAt,
                expiry ? DEFAULT_EXPIRATION : null
        );
    }

    public String generateToken(String username, LocalDateTime tokenAt, Long expiry) {
        return generateToken(
                username,
                Map.of(
                        jwtClaimName, tokenAt.atZone(ZoneId.systemDefault()).toEpochSecond(),
                        "database", "kerala_db",
                        "state", "Kerala"
                ),
                expiry
        );
    }

    public String generateToken(String subject, Map<String, Object> claims, Long expiry) {
        JwtBuilder.BuilderClaims builderClaims = Jwts.builder()
                .claims()
                .add(claims)
                .subject(subject)
                .issuedAt(new Date());

        if (null != expiry) {
            builderClaims.expiration(new Date(System.currentTimeMillis() + expiry));
        }

        return builderClaims.and()
                .signWith(getKey())
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return !isTokenExpired(token) && extractSubject(token).equals(userDetails.getUsername());
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUAT(String token) {
        return extractClaim(token, claims -> claims.get(jwtClaimName, Long.class));
    }

    public String extractToken(String authorizationValue) {
        if (StringUtils.isNotBlank(authorizationValue) && isBearer(authorizationValue)) {
            return authorizationValue.substring(7);
        }

        throw new UnauthorizedException(true);
    }

    public boolean isBearer(String authorizationValue) {
        return authorizationValue.startsWith("Bearer ");
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception exception) {
            throw new JwtException(exception.getMessage());
        }
    }

    private boolean isTokenExpired(String token) {
        Date date = extractExpiration(token);
        return null != date && date.before(new Date());
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}