package study.stomp.stompstudy.domain.message.normal.dto.response;

import lombok.*;
import study.stomp.stompstudy.domain.file.domain.UploadFile;
import study.stomp.stompstudy.domain.model.ActionType;
import study.stomp.stompstudy.domain.model.ChatType;
import study.stomp.stompstudy.infra.kafka.producer.chat.event.NormalChatEvent;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalMessageCreateResponse {

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

        public static NormalMessageCreateResponse from(NormalChatEvent chatEvent){
            return NormalMessageCreateResponse.builder()
                    .messageId(chatEvent.getMessageId())
                    .normalId(chatEvent.getNormalId())
                    .userId(chatEvent.getUserId())
                    .profileImg(chatEvent.getProfileImg())
                    .writer(chatEvent.getWriter())
                    .content(chatEvent.getContent())
                    .isDeleted(chatEvent.getIsDeleted())
                    .chatType(chatEvent.getChatType())
                    .actionType(chatEvent.getActionType())
                    .files(chatEvent.getFiles())
                    .createdAt(chatEvent.getCreatedAt())
                    .modifiedAt(chatEvent.getModifiedAt())
                    .build();
        }
}
