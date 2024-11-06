package study.stomp.stompstudy.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.stomp.stompstudy.global.common.dto.response.ErrorResponseDto;
import study.stomp.stompstudy.global.exception.Code;
import study.stomp.stompstudy.global.exception.GlobalException;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class StompExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    protected ErrorResponseDto handleGlobalException(GlobalException e){
        log.error("GlobalException: {}", e.getErrorCode().getMessage());
        return ErrorResponseDto.of(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: {}", e.getDetailMessageArguments());
        return ErrorResponseDto.of(Code.VALIDATION_ERROR, "잘못된 값 전달");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponseDto handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException: {}", e.getMessage());
        return ErrorResponseDto.of(Code.INVALID_JSON_FORMAT, "JSON 형식 에러");
    }

    @ExceptionHandler(IOException.class)
    public ErrorResponseDto handleIOException(IOException e) {
        log.error("IOException: {}", e.getMessage());
        return ErrorResponseDto.of(Code.IO_ERROR, "IO Error");
    }
}
