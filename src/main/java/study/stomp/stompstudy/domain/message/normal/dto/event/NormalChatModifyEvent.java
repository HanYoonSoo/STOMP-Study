package study.stomp.stompstudy.domain.message.normal.dto.event;

import lombok.*;
import study.stomp.stompstudy.domain.message.normal.domain.NormalMessage;
import study.stomp.stompstudy.domain.model.ActionType;
import study.stomp.stompstudy.domain.model.ChatType;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalChatModifyEvent {

    private String uuid;
    private Long normalId;
    private Long messageId;
    private String content;
    private ChatType chatType;
    private ActionType actionType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static NormalChatModifyEvent from(NormalMessage normalMessage, String uuid) {
        return NormalChatModifyEvent.builder()
                .uuid(uuid)
                .normalId(normalMessage.getNormalId())
                .messageId(normalMessage.getMessageId())
                .content(normalMessage.getContent())
                .chatType(normalMessage.getChatType())
                .actionType(normalMessage.getActionType())
                .createdAt(normalMessage.getCreatedAt())
                .modifiedAt(normalMessage.getModifiedAt())
                .build();
    }
}
