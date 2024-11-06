package study.stomp.stompstudy.global.security.custom;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import study.stomp.stompstudy.global.exception.AccountException;
import study.stomp.stompstudy.global.exception.Code;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        writePrintErrorResponse(response, exception);
    }

    private void writePrintErrorResponse(HttpServletResponse response, AuthenticationException exception) throws IOException {
        if(exception instanceof BadCredentialsException){
            sendExceptionResponse("아이디/비밀번호 불일치");
        }
        else if(exception instanceof AccountExpiredException){
            sendExceptionResponse("계정만료");
        }
        else if(exception instanceof UsernameNotFoundException){
            sendExceptionResponse("계정없음");
        }
        else if(exception instanceof CredentialsExpiredException){
            sendExceptionResponse("비밀번호 만료");
        }
        else if(exception instanceof DisabledException){
            sendExceptionResponse("계정 비활성화");
        }
        else if(exception instanceof LockedException){
            sendExceptionResponse("계정 잠김");
        }
        else{
            sendExceptionResponse("확인된 에러가 없습니다.");
        }
    }

    // JWT 예외처리
    private void sendExceptionResponse(String message) throws IOException {
        throw new AccountException(Code.BAD_REQUEST, message);
    }
}
