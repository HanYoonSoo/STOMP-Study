package study.stomp.stompstudy.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public enum Code {
    OK(0, HttpStatus.OK, "Ok"),

    BAD_REQUEST(30000, HttpStatus.BAD_REQUEST, "Bad request"),
    VALIDATION_ERROR(30001, HttpStatus.BAD_REQUEST, "Validation error"),
    NOT_FOUND(30002, HttpStatus.NOT_FOUND, "Requested resource is not found"),
    INVALID_JSON_FORMAT(30003, HttpStatus.BAD_REQUEST, "Invalid Json Format"),

    INTERNAL_ERROR(30010, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    DATA_ACCESS_ERROR(30011, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error"),

    UNAUTHORIZED(30020, HttpStatus.UNAUTHORIZED, "User unauthorized"),
    IO_ERROR(30021, HttpStatus.INTERNAL_SERVER_ERROR, "IO_ERROR"),
    FORBIDDEN(30022, HttpStatus.FORBIDDEN, "User Forbidden"),

    EXPIRE_ACCESS_TOKEN(30030, HttpStatus.UNAUTHORIZED, "Token Expired"),
    EMPTY_REFRESH_TOKEN(30031, HttpStatus.UNAUTHORIZED, "Empty Refresh Token"),
    REISSUE_FAIL(30032, HttpStatus.UNAUTHORIZED, "Token Reissue Fail"),
    NOT_VALID_REFRESH_TOKEN(30033, HttpStatus.UNAUTHORIZED, "Not Valid Refresh Token"),
    TOKEN_ERROR(30034, HttpStatus.BAD_REQUEST, "Token Error"),
    EXPIRE_REFRESH_TOKEN(30030, HttpStatus.UNAUTHORIZED, "Refresh Token Expired");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }
}
