package com.ilcle.ilcle_back.security.jwt;

import com.ilcle.ilcle_back.entity.RefreshToken;
import com.ilcle.ilcle_back.exception.ErrorCode;
import com.ilcle.ilcle_back.repository.RefreshTokenRepository;
import com.ilcle.ilcle_back.security.user.UserDetailsServiceImpl;
import com.ilcle.ilcle_back.utils.ValidateCheck;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final long ACCESS_TIME = 2 * 60 * 60 * 1000L;
    private static final long REFRESH_TIME = 7 * 24 * 60 * 60 * 1000L;
    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64Utils.decodeFromUrlSafeString(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오는 기능
    public String getHeaderToken(HttpServletRequest request, String type) {
        return type.equals("Access") ? request.getHeader(ACCESS_TOKEN) : request.getHeader(REFRESH_TOKEN);
    }

    // 토큰 생성
    public TokenDto createAllToken(String username) {
        return new TokenDto(createToken(username, "Access"), createToken(username, "Refresh"));
    }

    public String createToken(String username, String type) {

        Date date = new Date();

        long time = type.equals("Access") ? ACCESS_TIME : REFRESH_TIME;

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(date.getTime() + time))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    // 토큰 검증
    public boolean tokenValidation(String token, HttpServletRequest request) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
            // 잘못된 타입의 토큰
        } catch (SecurityException | MalformedJwtException e) {
            request.setAttribute("exception", ErrorCode.WRONG_TYPE_TOKEN.getMessage());

            // 만료된 토큰
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getMessage());

            // 지원하지 않는 토큰
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", ErrorCode.UNSUPPORTED_TOKEN.getMessage());

            // 잘못된 토큰
        } catch (io.jsonwebtoken.security.SignatureException e) {
            request.setAttribute("exception", ErrorCode.WRONG_TOKEN.getMessage());

        } catch (Exception e) {
            log.error("================================================");
            log.error("JwtFilter - doFilterInternal() 오류발생");
            log.error("token : {}", token);
            log.error("Exception Message : {}", e.getMessage());
            log.error("Exception StackTrace : {");
            e.printStackTrace();
            log.error("}");
            log.error("================================================");
            request.setAttribute("exception", ErrorCode.UNDEFINED_ERROR.getMessage());
        }
        return false;
    }

    // refreshToken 토큰 검증
    public boolean refreshTokenValidation(String token,HttpServletRequest request) {

        // 1차 토큰 검증
        if (!tokenValidation(token, request)) return false;

        // DB에 저장한 토큰 비교
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberUsername(getUsernameFromToken(token));

        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 username 가져오는 기능 복호화
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰 삭제

}