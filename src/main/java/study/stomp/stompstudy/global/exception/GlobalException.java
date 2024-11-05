package study.stomp.stompstudy.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException{
    private Code errorCode;
    private String message;
}
