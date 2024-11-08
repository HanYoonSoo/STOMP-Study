package study.stomp.stompstudy.domain.message.normal.dto.event;

import lombok.*;
import study.stomp.stompstudy.domain.file.domain.UploadFile;
import study.stomp.stompstudy.domain.message.normal.domain.NormalMessage;
import study.stomp.stompstudy.domain.model.ActionType;
import study.stomp.stompstudy.domain.model.ChatType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalChatCreateEvent {

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

    public static NormalChatCreateEvent from(NormalMessage normalMessage, String uuid) {
        return NormalChatCreateEvent.builder()
                .uuid(uuid)
                .messageId(normalMessage.getMessageId())
                .normalId(normalMessage.getNormalId())
                .userId(normalMessage.getUserId())
                .profileImg(normalMessage.getProfileImg())
                .writer(normalMessage.getWriter())
                .content(normalMessage.getContent())
                .isDeleted(normalMessage.isDeleted())
                .chatType(normalMessage.getChatType())
                .actionType(normalMessage.getActionType())
                .files(normalMessage.getFiles())
                .createdAt(normalMessage.getCreatedAt())
                .modifiedAt(normalMessage.getModifiedAt())
                .build();
    }
}
