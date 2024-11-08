package study.stomp.stompstudy.domain.message.normal.dto.response;

import lombok.*;
import study.stomp.stompstudy.domain.message.normal.dto.request.NormalMessageDeleteRequest;
import study.stomp.stompstudy.domain.model.ActionType;
import study.stomp.stompstudy.domain.model.ChatType;
import study.stomp.stompstudy.infra.kafka.producer.chat.event.NormalChatEvent;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalMessageDeleteResponse {

    private Long messageId;
    private ChatType chatType;
    private ActionType actionType;

    public static NormalMessageDeleteResponse from(NormalChatEvent normalChatEvent){
        return NormalMessageDeleteResponse.builder()
                .messageId(normalChatEvent.getMessageId())
                .chatType(normalChatEvent.getChatType())
                .actionType(normalChatEvent.getActionType())
                .build();
    }
}
