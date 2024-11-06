package study.stomp.stompstudy.global.exception;

public class AccountException extends FilterException{
    public AccountException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
