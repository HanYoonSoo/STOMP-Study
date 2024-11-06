package study.stomp.stompstudy.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import study.stomp.stompstudy.global.common.dto.response.ResponseDto;
import study.stomp.stompstudy.global.exception.AccountException;
import study.stomp.stompstudy.global.exception.FilterException;
import study.stomp.stompstudy.global.exception.JwtException;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException | AccountException e) {
            setExceptionResponse(response, e);
        }
    }

    private void setExceptionResponse(HttpServletResponse response, FilterException e) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter()
                .write(
                        objectMapper.writeValueAsString(
                                new ResponseDto(false, e.getErrorCode().getCode(), e.getMessage())
                        )
                );
    }
}