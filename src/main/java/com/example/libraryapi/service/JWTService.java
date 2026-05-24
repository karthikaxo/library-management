package com.example.libraryapi.service;

import com.example.libraryapi.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    private static final String SECRET = "9834dn943842090dshkfdhfhdsklhdskhdshk392383483902384s";

    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 1 day

    public String generateToken(Account account) {

        Map<String, String> claims = new HashMap<>();

        claims.put("role", account.getRole().name());

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(account.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .and()
                .signWith(getKey())
                .compact();
    }

    // create signing key
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // extract username from token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // extract all claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // validate token
    public boolean validateToken(String token, UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // token expiration
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // extract expiry date
    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
}
