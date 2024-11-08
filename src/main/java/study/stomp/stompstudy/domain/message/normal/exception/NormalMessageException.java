package study.stomp.stompstudy.domain.message.normal.exception;

import study.stomp.stompstudy.global.exception.Code;
import study.stomp.stompstudy.global.exception.GlobalException;

public class NormalMessageException extends GlobalException {
    public NormalMessageException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
