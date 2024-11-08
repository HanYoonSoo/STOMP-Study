package study.stomp.stompstudy.domain.message.normal.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalMessageDeleteRequest {

    private Long normalId;
    private Long messageId;
}
