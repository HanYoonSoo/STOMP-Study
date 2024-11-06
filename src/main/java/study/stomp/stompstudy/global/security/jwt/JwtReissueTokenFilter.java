package study.stomp.stompstudy.global.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import study.stomp.stompstudy.domain.model.RoleType;
import study.stomp.stompstudy.global.common.dto.security.TokenDto;
import study.stomp.stompstudy.global.common.service.RedisService;
import study.stomp.stompstudy.global.exception.Code;
import study.stomp.stompstudy.global.exception.JwtException;
import study.stomp.stompstudy.global.security.custom.CustomUserDetails;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtReissueTokenFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final RedisService redisService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        String method = request.getMethod();

        if(path.equals("/auth/reissue") && method.equals("PATCH")){
            String accessToken = jwtProvider.resolveAccessToken(request);

            boolean isExpired;

            try{
                isExpired = jwtProvider.isExpired(accessToken);
            } catch (ExpiredJwtException e){
                isExpired = true;
            }

            if(!isExpired){
                jwtProvider.accessTokenSetHeader(accessToken, response);
                return;
            }

            String refreshToken = jwtProvider.resolveRefreshToken(request);

//            System.out.println(refreshToken);
            if(!StringUtils.hasText(refreshToken))
                throw new JwtException(Code.EMPTY_REFRESH_TOKEN, "Refresh Token is Empty");

            try{
                jwtProvider.isExpired(refreshToken);
            } catch (ExpiredJwtException e){
                throw new JwtException(Code.EXPIRE_REFRESH_TOKEN, "Refresh Token is Expired");
            }

            String loginId = jwtProvider.getUserSubject(refreshToken);
            String redisRefreshToken = redisService.getValues(loginId);

//            System.out.println(loginId + " " + redisRefreshToken);

            if(redisService.checkExistsValue(redisRefreshToken) && refreshToken.equals(redisRefreshToken)){
                String role = jwtProvider.getRole(refreshToken);
                CustomUserDetails userDetails = CustomUserDetails.of(loginId, RoleType.fromRoleType(role));
                TokenDto tokenDto = jwtProvider.generateToken(userDetails);
                jwtProvider.accessTokenSetHeader(tokenDto.getAccessToken(), response);
                return;
            }

            throw new JwtException(Code.REISSUE_FAIL, "토큰 재발급에 실패했습니다.");
        }

        filterChain.doFilter(request, response);
    }
}
