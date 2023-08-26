package com.myown.board.jwt.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
/**
 * AuthenticationEntryPoint
 *
 * 인증 과정에서 실패하거나 인증을 위한 헤더정보를 보내지 않은 경우
 * 401(UnAuthorized) 에러가 발생하게 된다.
 *
 * Spring Security에서 인증되지 않은 사용자에 대한 접근 처리는 AuthenticationEntryPoint가 담당하는데,
 * commence 메소드가 실행되어 처리된다.
 */

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e
    ) throws IOException {
        System.out.println(request.getRequestURI());
        log.error("UnAuthorized -- message : " + e.getMessage()); // 로그를 남기고
//        response.sendRedirect("/users/signup"); // 로그인 페이지로 리다이렉트되도록 하였다.
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
//        // 원하는 추가적인 응답 정보 설정 가능
//        response.setContentType("application/json");
//        response.getWriter().write("Unauthorized"); // JSON 형태의 응답 메시지 설정
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
