package com.myown.board.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtProvider {
    // application.yml 에 정의한 만료시간 가져오기
    @Value("${spring.jwt.token.access-expiration-time}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${spring.jwt.token.refresh-expiration-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    private static final String AUTHORITIES_KEY = "auth";

    private final UserDetailsService userDetailsService;

    private final Key key;

    @Autowired
    // application.yml 에 정의된 jwt.secret 값을 가져와 JWT 를 만들 때 사용하는 암호화 키값을 생성
    public JwtProvider(@Value("${spring.jwt.secret}") String secretKey, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        log.info("key : {}", key);
    }

    // 토큰 생성
    public TokenResponse generateTokenDto(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())           // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)            // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn)            // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)        // header "alg": "HS512"
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenResponse.builder()
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    // 인증 정보 추출
    public Authentication getAuthentication(String token) {
        String username = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // http 헤더에서 bearer 토큰 추출
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Access 토큰을 검증
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            // MalformedJwtException | ExpiredJwtException | IllegalArgumentException
            throw new IllegalArgumentException("Error on Token");
        }
    }
}