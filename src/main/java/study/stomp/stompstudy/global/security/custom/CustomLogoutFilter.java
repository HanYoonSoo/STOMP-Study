package study.stomp.stompstudy.global.security.custom;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import study.stomp.stompstudy.global.common.enums.ExpiredTimeEnum;
import study.stomp.stompstudy.global.common.service.RedisService;
import study.stomp.stompstudy.global.exception.Code;
import study.stomp.stompstudy.global.exception.JwtException;
import study.stomp.stompstudy.global.security.jwt.JwtProvider;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
public class CustomLogoutFilter extends OncePerRequestFilter {
    private final RedisService redisService;
    private final JwtProvider jwtProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain filterChain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();

        if (!requestURI.matches("/auth/logout") || !requestMethod.equals("PATCH")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtProvider.resolveAccessToken(request);

        String loginId;
        try{
            loginId = jwtProvider.getUserSubject(accessToken);
        } catch (ExpiredJwtException e){
            throw new JwtException(Code.EXPIRE_ACCESS_TOKEN, "Access Token is Expired");
        }

        // 로그인 ID에 해당하는 Redis 값 삭제
        redisService.deleteValues(loginId);

        // 로그아웃 시 Access Token Redis 저장 (key = Access Token / value = "logout")
        long accessTokenValidityInSeconds = ExpiredTimeEnum.ACCESS_TOKEN.getExpiredTime();
        redisService.setValues(accessToken, "logout", Duration.ofMillis(accessTokenValidityInSeconds));

        // 인증 컨텍스트 초기화
        SecurityContextHolder.clearContext();

        // 헤더 초기화
        response.setHeader(JwtProvider.AUTHORIZATION_HEADER, null);
        response.setHeader(JwtProvider.REFRESH_HEADER, null);

        response.setStatus(HttpStatus.OK.value());
    }
}