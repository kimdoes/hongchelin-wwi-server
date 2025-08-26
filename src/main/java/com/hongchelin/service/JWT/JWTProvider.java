/**
 * JWT 토큰을 실질적으로 만들고 유효성을 검증하는 클래스
 *
 * @author dx2d2y
 *
 * @param secret 토큰 발급 / 유효성 검사에 쓰이는 비밀키
 * @param identifier 유저 식별자 (이메일 또는 고유 id)
 * @param roles 유저 권한
 * @param token JWT 토큰
 *
 * @return createToken / createRefreshToken 에서는 주어진 정보를 통해 새 토큰을 반환
 * @return getSubject 에서는 토큰과 시크릿키를 주면 이를 평문으로 바꿈. 반환된 객체는 (String) claims.get("인자"); 를 통해 데이터에 접근해야야함
 * @return validateToken 에서는 토큰의 유효성을 검증함. 유효할 경우 "성공"을 반환, 그렇지 않을 경우 아닌 이유를 반환함
 */
package com.hongchelin.service.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTProvider {
    public String createToken(String secret, String identifier) {
        Long validTokenTime = 1000L * 60 * 60;
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        Claims claims = Jwts.claims();
        claims.put("identifier", identifier);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTokenTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String createRefreshToken(String secret, String identifier) {
        Long refreshTokenTime = 1000L * 60 * 60 * 24 * 7;
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        Claims claims = Jwts.claims();
        claims.put("identifier", identifier);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    //try - catch 구조 필요
    public Claims getSubject(String secret, String token) {
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        Claims claims = jwsClaims.getBody();

        return claims;
    }

    public String validateToken(String secret, String token) {
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return "만료된 JWT";
            }
            return "성공";
        } catch (ExpiredJwtException e) {
            return "만료된 JWT";
        } catch (SecurityException | MalformedJwtException e) {
            return "잘못된 타입";
        } catch (UnsupportedJwtException e) {
            return "잘못된 JWT 구조";
        } catch (io.jsonwebtoken.security.SignatureException e) {
            return "서명실패(위조데이터)";
        }
    }
}