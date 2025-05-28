package com.uniSync.uniSync_api.utils;

import org.springframework.stereotype.Component;
import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "secretKey";
    private final String ISSUER = "issuer";
    private final long EXPIRATION_TIME = 864_000_000;

    public String generatetoken(String issuer, String subject, long expiration) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration * 1000);

        return Jwts.builder()
                .setSubject(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512 ,SECRET_KEY)
                .compact();



    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean validateToken(String token, String username) {
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e) {
            return false;
        }


    }
}
