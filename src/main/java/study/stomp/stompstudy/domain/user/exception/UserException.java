package study.stomp.stompstudy.domain.user.exception;

import study.stomp.stompstudy.global.exception.Code;
import study.stomp.stompstudy.global.exception.GlobalException;

public class UserException extends GlobalException {
    public UserException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
