package study.stomp.stompstudy.global.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import study.stomp.stompstudy.global.exception.Code;
import study.stomp.stompstudy.global.exception.GlobalException;
import study.stomp.stompstudy.global.exception.JwtException;
import study.stomp.stompstudy.global.security.jwt.JwtProvider;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class AuthenticationChannelInterceptor implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
//        System.out.println("111111111");
        validateTokenOnConnect(headerAccessor);
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        ChannelInterceptor.super.postSend(message, channel, sent);
    }

    private void validateTokenOnConnect(StompHeaderAccessor headerAccessor) {
        if(StompCommand.CONNECT.equals(headerAccessor.getCommand())){
            String accessToken = Objects.requireNonNull(
                    headerAccessor.getFirstNativeHeader(JwtProvider.AUTHORIZATION_HEADER));

            if(!jwtProvider.validateToken(accessToken)){
                throw new JwtException(Code.UNAUTHORIZED, "Jwt Token Error");
            }
        }
    }
}
