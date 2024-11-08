package study.stomp.stompstudy.domain.message.normal.dto.event;

import lombok.*;
import study.stomp.stompstudy.domain.message.normal.domain.NormalMessage;
import study.stomp.stompstudy.domain.model.ActionType;
import study.stomp.stompstudy.domain.model.ChatType;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalChatDeleteEvent {

    private String uuid;
    private Long messageId;
    private ChatType chatType;
    private ActionType actionType;

    public static NormalChatDeleteEvent from(NormalMessage normalMessage, String uuid) {
        return NormalChatDeleteEvent.builder()
                .uuid(uuid)
                .messageId(normalMessage.getMessageId())
                .chatType(normalMessage.getChatType())
                .actionType(normalMessage.getActionType())
                .build();
    }
}
