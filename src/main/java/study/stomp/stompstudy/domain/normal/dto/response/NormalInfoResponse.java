package study.stomp.stompstudy.domain.normal.dto.response;

import lombok.*;
import study.stomp.stompstudy.domain.normal.domain.Normal;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalInfoResponse {

    private String normalChatName;

    private List<String> userIds;


    public static NormalInfoResponse from(Normal normal) {
        return NormalInfoResponse.builder()
                .normalChatName(normal.getNormalChatName())
                .userIds(normal.getUserCodes())
                .build();
    }
}
