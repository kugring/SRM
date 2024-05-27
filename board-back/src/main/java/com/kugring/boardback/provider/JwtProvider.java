package com.kugring.boardback.provider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

    @Value("${secret-key}")
    private String secretKey;

    public String create(String email){
        // 자바의 util로 임폴트 되어진다.
        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        String jwt =  Jwts.builder()
        .signWith(SignatureAlgorithm.HS256, secretKey) //알고리즘 때문에 HS256 떠야한다!
        .setSubject(email).setIssuedAt(new Date()).setExpiration(expireDate)
        .compact();

        return jwt;

   }

    // jwt토큰을 받아서 검증하고 풀어서 페이로드에 있는 subject를 준다! 데이터타입은 이메일도 스트링이기 때문에 받는것도 스트링
    public String validate(String jwt){
        Claims claims = null;
        
        try {
            claims = Jwts.parser().setSigningKey(secretKey)
            .parseClaimsJws(jwt).getBody();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return claims.getSubject();
    }

    // 필터를 만들고 jwt를만들었고 http헤더에 오소리에전에다가 베어로 포함해서 날라올텐데 검증하고 필요한 부분에서 처리를 할 수 있는지 없는지 확인하는 코드
    
    
}
