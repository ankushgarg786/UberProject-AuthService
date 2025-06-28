package org.uberprojectauthservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public class JwtService {
    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private  String SECRET;

    private final SecretKey secretKey= Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * This method creates a brand-new JWT token based on a payload
     * @return
     */

    private String createToken(Map<String,Object>payload,String email){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry*1000L);
        return Jwts.builder()
                .claims(payload)
                .claim("phoneNumber", "1234567890")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(email)
                .signWith(secretKey)
                .compact();  //It will convert all into string
    }

    /**
     * This method will extract all the claims from the token
     * @param token
     * @return
     */
    private Claims extractClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * This will extract a particular claim example expirationDate etc
     * @param token
     * @param claimsResolver
     * @return
     * @param <T>
     */
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     *This method checks if the token expiry was before the current time stamp or not
     * @param token
     * @return true ,  if the token is expired else false
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Boolean validateToken(String token,String email) {
        final String userEmailFetchFromToken=extractEmail(token);
        return userEmailFetchFromToken.equals(email) && !isTokenExpired(token);
    }

    private String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     *
     * Just to know how we can extract claim if we have set using .claim("","") while creating jwt token
     * @param token
     * @return
     */
    private String extractPhoneNumber(String token) {
        //Method 1
        Claims allClaims=extractClaims(token);
        String phoneNumber=allClaims.get("phoneNumber",String.class);

        //Method 2
        return extractClaim(token, claims -> claims.get("phoneNumber",String.class));
    }

    /**
     * A utility function to get any value by just passing key and token, eg if we want phoneNumber just pass (token,"phoneNumber)
     * @param token
     * @param payloadKey
     * @return
     */
    private Object extractValue(String token,String payloadKey){
        Claims allClaims=extractClaims(token);

        return allClaims.get(payloadKey);
    }
}
