package study.stomp.stompstudy.global.exception;

public class JwtException extends FilterException {
    public JwtException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
