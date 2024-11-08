package study.stomp.stompstudy.global.utils;

import java.util.List;

public class UrlUtil {
    public static final List<String> EXCLUDE_URLS = List.of(
            "/auth/login", "/docs/index.html",
            "/user/signup", "/auth/reissue",
            "/ws-stomp", "/ws"
    );

    public static final List<String> PERMITTED_URLS = List.of(
            "/user/signup", "/auth/reissue", "/ws-stomp", "/ws/**"
    );
}
