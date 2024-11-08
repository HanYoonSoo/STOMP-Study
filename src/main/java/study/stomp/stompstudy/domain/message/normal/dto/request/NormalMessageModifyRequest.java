package study.stomp.stompstudy.domain.message.normal.dto.request;

import lombok.*;
import study.stomp.stompstudy.domain.model.ActionType;
import study.stomp.stompstudy.domain.model.ChatType;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalMessageModifyRequest {

    private Long normalId;
    private Long messageId;
    private String content;
}
