package study.stomp.stompstudy.global.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UUIDUtil {

    public static String generateUUID() {
        return java.util.UUID.randomUUID().toString();
    }
}
