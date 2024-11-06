package study.stomp.stompstudy.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.stomp.stompstudy.global.common.dto.security.LoginDto;
import study.stomp.stompstudy.global.common.dto.security.TokenDto;
import study.stomp.stompstudy.global.common.enums.ExpiredTimeEnum;
import study.stomp.stompstudy.global.common.service.RedisService;
import study.stomp.stompstudy.global.security.custom.CustomUserDetails;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // ServletInputStream을 LoginDto에 담기 위해 역직렬화 수행
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getLoginId(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal(); // CustomUserDetails 클래스의 객체를 얻음
        TokenDto tokenDto = jwtProvider.generateToken(customUserDetails);

        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();

        jwtProvider.accessTokenSetHeader(accessToken, response);
        jwtProvider.refreshTokenSetHeader(refreshToken, response);

        // 로그인 성공시 Refresh Token Redis 저장(key = Email / value = Refresh Token)
        long refreshTokenValidityInSeconds = ExpiredTimeEnum.REFRESH_TOKEN.getExpiredTime();

        redisService.setValues(
                customUserDetails.getLoginId(), refreshToken, Duration.ofMillis(refreshTokenValidityInSeconds)
        );

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
