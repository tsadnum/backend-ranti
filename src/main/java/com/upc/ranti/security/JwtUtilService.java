package com.upc.ranti.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtilService {
    // [Base64]
    private static final String JWT_SIGNATURE_KEY = "QVJRVUlURUNUVVJBX0FQTElDQUNJT05FU19XRUJfVVBDX0lOR0VOSUVSSUFfU0lTVEVNQVNfREVfSU5GT1JNQUNJT04K";
    private static final Long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * (long)3;

    private SecretKey getSigningKey(){
        byte[] decodedKey = Base64.getDecoder().decode(JWT_SIGNATURE_KEY);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsFunction) {
        return claimsFunction.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, SecurityUser user) {
        try {
            String username = extractUsername(token);
            return (!isTokenExpired(token)) && (username.equals(user.getUsername()));
        } catch (Exception e) {
            return false;
        }
    }


    private String createToken(String subject, Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSigningKey(), io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(SecurityUser securityUser) {
        Map<String, Object> claims = new HashMap<>();
        Object authorities = securityUser.getAuthorities().stream()
                .map(n -> String.valueOf(n.getAuthority()))
                .toList();
        claims.put("authorities", authorities);
        claims.put("usuarioId", securityUser.getUsuarioId()); // NUEVO
        return createToken(securityUser.getUsername(), claims);
    }

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("usuarioId", Long.class);
    }
}