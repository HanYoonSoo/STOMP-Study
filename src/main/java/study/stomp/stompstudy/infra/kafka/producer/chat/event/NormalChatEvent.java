package study.stomp.stompstudy.infra.kafka.producer.chat.event;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import study.stomp.stompstudy.domain.file.domain.UploadFile;
import study.stomp.stompstudy.domain.message.normal.dto.NormalMessageDto;
import study.stomp.stompstudy.domain.message.normal.dto.event.NormalChatCreateEvent;
import study.stomp.stompstudy.domain.message.normal.dto.event.NormalChatDeleteEvent;
import study.stomp.stompstudy.domain.message.normal.dto.event.NormalChatModifyEvent;
import study.stomp.stompstudy.domain.model.ActionType;
import study.stomp.stompstudy.domain.model.ChatType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "normalChatEvent")
public class NormalChatEvent {

    @Transient
    public static final String SEQUENCE_NAME = "normal_chat_event_sequence";

    @Id
    private Long eventId;

    private EventSentType eventSentType;

    private String uuid;

    private Long messageId;
    private Long normalId;
    private Long userId;
    private String profileImg;
    private String writer;
    private String content;
    private Boolean isDeleted;
    private ChatType chatType;
    private ActionType actionType;
    private List<UploadFile> files;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public void generateSequence(Long eventId){
        this.eventId = eventId;
    }

    public static NormalChatEvent from(NormalChatCreateEvent message, EventSentType eventSentType) {
        return NormalChatEvent.builder()
                .uuid(message.getUuid())
                .messageId(message.getMessageId())
                .normalId(message.getNormalId())
                .userId(message.getUserId())
                .profileImg(message.getProfileImg())
                .writer(message.getWriter())
                .content(message.getContent())
                .isDeleted(message.getIsDeleted())
                .actionType(message.getActionType())
                .files(message.getFiles())
                .createdAt(message.getCreatedAt())
                .modifiedAt(message.getModifiedAt())
                .eventSentType(eventSentType)
                .build();
    }

    public static NormalChatEvent from(NormalChatModifyEvent message, EventSentType eventSentType) {
        return NormalChatEvent.builder()
                .uuid(message.getUuid())
                .messageId(message.getMessageId())
                .normalId(message.getNormalId())
                .content(message.getContent())
                .actionType(message.getActionType())
                .createdAt(message.getCreatedAt())
                .modifiedAt(message.getModifiedAt())
                .eventSentType(eventSentType)
                .build();
    }

    public static NormalChatEvent from(NormalChatDeleteEvent message, EventSentType eventSentType) {
        return NormalChatEvent.builder()
                .uuid(message.getUuid())
                .messageId(message.getMessageId())
                .actionType(message.getActionType())
                .eventSentType(eventSentType)
                .build();
    }

    public void changeEventSentType(EventSentType eventSentType) {
        this.eventSentType = eventSentType;
    }
}
