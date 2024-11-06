package study.stomp.stompstudy.global.security.jwt;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import study.stomp.stompstudy.global.common.service.RedisService;
import study.stomp.stompstudy.global.exception.Code;
import study.stomp.stompstudy.global.exception.JwtException;
import study.stomp.stompstudy.global.utils.UrlUtils;

@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final RedisService redisService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
        String accessToken = jwtProvider.resolveAccessToken(request);

        if(!StringUtils.hasText(accessToken)){
            throw new JwtException(Code.TOKEN_ERROR, "Empty Access Token");
        }

        try {
            if(jwtProvider.validateToken(accessToken) && doNotLogout(accessToken)){
                setAuthenticationToContext(accessToken);
            } else{
                filterChain.doFilter(request, response);
                return;
            }
        } catch (JwtException e) {
            throw new JwtException(e.getErrorCode(), e.getMessage());
        }
        filterChain.doFilter(request, response);
    }


    private void setAuthenticationToContext(String accessToken) throws java.io.IOException {
        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        log.info("Authentication: {}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("# Token verification success!");
    }

    private boolean doNotLogout(String accessToken) {
        String isLogout = redisService.getValues(accessToken);
        return isLogout.equals("false");
    }

    // EXCLUDE_URL과 동일한 요청이 들어왔을 경우, 현재 필터를 진행하지 않고 다음 필터 진행
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String servletPath = request.getServletPath();
        return UrlUtils.EXCLUDE_URLS.stream().anyMatch(servletPath::startsWith);
    }
}