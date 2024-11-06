package study.stomp.stompstudy.global.security.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import study.stomp.stompstudy.global.common.dto.response.DataResponseDto;
import study.stomp.stompstudy.global.common.dto.security.LoginResponse;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        LoginResponse loginResponse = LoginResponse.from(customUserDetails);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(converObjectToJson(DataResponseDto.from(loginResponse)));
    }

    private String converObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        return objectMapper.writeValueAsString(object);
    }
}
