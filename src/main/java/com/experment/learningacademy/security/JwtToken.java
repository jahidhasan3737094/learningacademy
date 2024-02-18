package com.experment.learningacademy.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtToken {

    private static final String SECRET_KEY = "Bearer";
    /*
    it contains user details,
    when it is created ,
    when it is going to expire,
    security algoriths its using,
    roles we have.
     */
    //token creation

    public String getUserNameFromToken(String token){
        return getClaimFromtoken(token, Claims::getSubject);
    }

    private <T> T  getClaimFromtoken(String token, Function<Claims,T> claimsResolver) {
        final Claims claims=getAllClaimFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<String,Object>();
        return dogenerateToken(claims,userDetails.getUsername());
    }

    private String dogenerateToken(Map<String, Object> claims, String username) {
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(
                new Date(System.currentTimeMillis())
        ).setExpiration(new Date(System.currentTimeMillis()+3600000))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();
    }
    //token validation


    ///token expiration

    public boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromtoken(token,Claims::getExpiration);
    }

    //token validation
    public boolean validateToken(String token,UserDetails userDetails){
        final String username= getUserNameFromToken(token);
        return username.equals(userDetails.getUsername())&& !isTokenExpired(token);
    }


}
