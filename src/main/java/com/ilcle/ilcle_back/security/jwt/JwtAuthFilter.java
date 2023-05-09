package com.ilcle.ilcle_back.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtUtil.getHeaderToken(request, "Access");
        String refreshToken = jwtUtil.getHeaderToken(request, "Refresh");

        if(accessToken != null) {
            if(!jwtUtil.tokenValidation(accessToken, request)){
//                jwtExceptionHandler(response, "AccessToken Expired", HttpStatus.UNAUTHORIZED);
                return;
            }
            setAuthentication(jwtUtil.getUsernameFromToken(accessToken));
        }else if(refreshToken != null) {
            if(!jwtUtil.refreshTokenValidation(refreshToken,request)){
//                jwtExceptionHandler(response, "RefreshToken Expired", HttpStatus.BAD_REQUEST);
                return;
            }
            setAuthentication(jwtUtil.getUsernameFromToken(refreshToken));
        }

        filterChain.doFilter(request,response);
    }

    public void setAuthentication(String username) {
        Authentication authentication = jwtUtil.createAuthentication(username);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

//    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
//        response.setStatus(status.value());
//        response.setContentType("application/json");
//        try {
//            ResponseEntity<String> entity = ResponseEntity.status(status).body(msg);
//            String json = new ObjectMapper().writeValueAsString(entity);
//            response.getWriter().write(json);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }
}
