package com.ecommerce.fullstack.code.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${SECRET_KEY}")
    public String key;

    public String getUserNameFromToken(String token){
       return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver){
      final Claims claims = getAllClaimsFromToken(token);
      return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String name = getUserNameFromToken(token);
        return ( name.equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }

    private boolean isTokenExpired(String token){
       final Date expire = getExpirationDateFromToken(token);
       return expire.before(new Date());
    }

    private Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public static final int TOKEN_VALIDITY = 3600 * 5 ;

   public String generateToken(UserDetails userDetails){
       Map<String, Object> claims = new HashMap<>();
       return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
               .signWith(SignatureAlgorithm.HS512, key)
               .compact();
   }


}
