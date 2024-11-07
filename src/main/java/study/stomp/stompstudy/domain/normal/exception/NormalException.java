package study.stomp.stompstudy.domain.normal.exception;

import study.stomp.stompstudy.global.exception.Code;
import study.stomp.stompstudy.global.exception.GlobalException;

public class NormalException extends GlobalException {
    public NormalException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
