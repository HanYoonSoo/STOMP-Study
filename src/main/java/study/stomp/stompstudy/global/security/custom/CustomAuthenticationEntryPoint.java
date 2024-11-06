package study.stomp.stompstudy.global.security.custom;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import study.stomp.stompstudy.global.exception.Code;
import study.stomp.stompstudy.global.exception.JwtException;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("인증되지 않은 사용자 접근");
        throw new JwtException(Code.UNAUTHORIZED, "인증되지 않은 사용자의 접근");
    }

}