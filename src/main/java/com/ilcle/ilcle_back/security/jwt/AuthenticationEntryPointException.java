package com.ilcle.ilcle_back.security.jwt;

import com.ilcle.ilcle_back.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointException implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");

        // 토큰 없는 경우
        if(exception == null) {
            setResponse(response, ErrorCode.NEED_TO_LOGIN);
        }
        // 잘못된 타입의 토큰인 경우
        else if(exception.equals(ErrorCode.WRONG_TYPE_TOKEN.getMessage())) {
            setResponse(response, ErrorCode.WRONG_TYPE_TOKEN);
        }
        // 토큰 만료된 경우
        else if(exception.equals(ErrorCode.EXPIRED_TOKEN.getMessage())) {
            setResponse(response, ErrorCode.EXPIRED_TOKEN);
        }
        // 지원되지 않는 토큰인 경우
        else if(exception.equals(ErrorCode.UNSUPPORTED_TOKEN.getMessage())) {
            setResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
        }
        // 잘못된 토큰인 경우
        else if(exception.equals(ErrorCode.WRONG_TOKEN.getMessage())) {
            setResponse(response, ErrorCode.WRONG_TOKEN);
        }
        else if(exception.equals(ErrorCode.UNDEFINED_ERROR.getMessage())) {
            setResponse(response, ErrorCode.UNDEFINED_ERROR);
        }
    }
    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("httpStatus", errorCode.getHttpStatus());
        responseJson.put("message", errorCode.getMessage());
        responseJson.put("detail", errorCode.getDetail());

        response.getWriter().print(responseJson);

//        response.getWriter().println(
//                new ObjectMapper().writeValueAsString(
//                        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                )
//        );
    }
}
