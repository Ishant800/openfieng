package com.example.user_service.JWT;

import com.example.user_service.dto.JwtTokenClaimsDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;


@Component
public class JwtService {


    @Value("${jwt.secrete}")
    private String secrete;

    @Value("${jwt.expiration-ms}")
    private String jwtExpiration;
    public String generateToken(JwtTokenClaimsDto dto){

        return Jwts.builder()
                .setSubject(String.valueOf(dto.getUserid()))
                .claim("username",dto.getUsername())
                .claim("email",dto.getEmail())
                .claim("role",dto.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, secrete)
                .compact();
    }

//    // extracting username
//    public String extractUsername(String token){
//        return extractAllClaims(token).getSubject();
//    }
//
//    // extracting role
//    public String extractRole(String token){
//        return extractAllClaims(token).get("role",String.class);
//    }
//
//    // extracting id
//    public Long extractId(String token){
//        return extractAllClaims(token).get("id",Long.class);
//    }
//
//
//    private Claims extractAllClaims(String token){
//        return Jwts.parser()
//                .setSigningKey(SECRET)
//                .parseClaimsJws(token)
//                .getBody();
//    }

    //token validation
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(secrete).build().parseClaimsJws(token).getBody();
            return true;
        }
        catch (JwtException ex){
            return false;
        }

    }

    public Claims getClaims(String token){
        return Jwts.parserBuilder().setSigningKey(secrete).build().parseClaimsJws(token).getBody();
    }




}
