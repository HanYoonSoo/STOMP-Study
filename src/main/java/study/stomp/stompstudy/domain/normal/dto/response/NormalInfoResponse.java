package study.stomp.stompstudy.domain.normal.dto.response;

import lombok.*;
import study.stomp.stompstudy.domain.normal.domain.Normal;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalInfoResponse {

    private Long normalId;
    private String normalChatName;

    public static NormalInfoResponse from(Normal normal) {
        return NormalInfoResponse.builder()
                .normalId(normal.getNormalId())
                .normalChatName(normal.getNormalChatName())
                .build();
    }
}
