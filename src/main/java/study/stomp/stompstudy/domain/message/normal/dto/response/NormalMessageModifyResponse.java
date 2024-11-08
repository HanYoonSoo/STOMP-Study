package study.stomp.stompstudy.domain.message.normal.dto.response;

import lombok.*;
import study.stomp.stompstudy.domain.model.ActionType;
import study.stomp.stompstudy.domain.model.ChatType;
import study.stomp.stompstudy.infra.kafka.producer.chat.event.NormalChatEvent;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalMessageModifyResponse {

    private Long normalId;
    private Long messageId;
    private String content;
    private ChatType chatType;
    private ActionType actionType;
    private LocalDateTime modifiedAt;

    public static NormalMessageModifyResponse from(NormalChatEvent normalChatEvent){
        return NormalMessageModifyResponse.builder()
                .normalId(normalChatEvent.getNormalId())
                .messageId(normalChatEvent.getMessageId())
                .content(normalChatEvent.getContent())
                .chatType(normalChatEvent.getChatType())
                .actionType(normalChatEvent.getActionType())
                .modifiedAt(normalChatEvent.getModifiedAt())
                .build();
    }
}
