package com.example.jpaexample.modules.auth.application.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import java.util.Date;

@Slf4j
public class JwtTokenProvider {
    private static final String JWT_SECRET = "secretkey";

    // 토큰 유효시간
    private static final int JWT_EXPIRATION_MS = 604800000;

    // jwt 토큰 생성
    public static String generateToken(Authentication authentication){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject((String) authentication.getPrincipal()) // 사용자
                .setIssuedAt(new Date()) // 현재 시간 기반으로 생성
                .setExpiration(expiryDate) // 만료 시간 셋팅
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET) // 사용할 암호화 알고리즘, signatured에 들어갈 secret 값 셋팅
                .compact();
    }

    // Jwt 토큰에서 아이디 추출
    public static String getUserIdFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Jwt 토큰 유효성 검사
    public static boolean validateTokne(String token){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

}
