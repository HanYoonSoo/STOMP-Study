package study.stomp.stompstudy.global.common.dto;

import lombok.Getter;
import study.stomp.stompstudy.global.exception.Code;

@Getter
public class DataResponseDto<T> extends ResponseDto {

    private final T data;

    private DataResponseDto(T data) {
        super(true, Code.OK.getCode(), Code.OK.getMessage());
        this.data = data;
    }

    private DataResponseDto(T data, String message) {
        super(true, Code.OK.getCode(), message);
        this.data = data;
    }

    public static <T> DataResponseDto<T> from(T data) {
        return new DataResponseDto<>(data);
    }

    public static <T> DataResponseDto<T> of(T data, String message) {
        return new DataResponseDto<>(data, message);
    }

    public static <T> DataResponseDto<T> empty() {
        return new DataResponseDto<>(null);
    }
}