package study.stomp.stompstudy.domain.message.normal.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import study.stomp.stompstudy.domain.file.domain.UploadFile;
import study.stomp.stompstudy.domain.message.normal.dto.request.NormalMessageCreateRequest;
import study.stomp.stompstudy.domain.model.ActionType;
import study.stomp.stompstudy.domain.model.BaseModel;
import study.stomp.stompstudy.domain.model.ChatType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "normal_messages")
public class NormalMessage extends BaseModel {

    @Transient
    public static final String SEQUENCE_NAME = "normal_messages_sequence";

    @Id
    private Long messageId;

    @Field
    private Long normalId;

    @Field
    private Long userId;

    @Field
    private String writer;

    @Field
    private String content;

    @Field
    private String profileImg;

    @Field
    private ChatType chatType;

    @Field
    private ActionType actionType;

    @Field
    private List<UploadFile> files;

    @Field
    private boolean isDeleted = Boolean.FALSE;

    public static NormalMessage from(NormalMessageCreateRequest request) {
        NormalMessage normalMessage = new NormalMessage();

        normalMessage.initNormalMessage(request);

        return normalMessage;
    }

    private void initNormalMessage(NormalMessageCreateRequest request) {
        this.normalId = request.getNormalId();
        this.userId = request.getUserId();
        this.profileImg = request.getProfileImg();
        this.writer = request.getWriter();
        this.content = request.getContent();
        this.chatType = ChatType.NORMAL;
        this.actionType = ActionType.SEND;
        this.files = request.getFiles();
    }

    public void generateSequence(Long messageId){
        this.messageId = messageId;
    }

    public void modify(String content) {
        this.content = content;
        this.actionType = ActionType.MODIFY;
        this.setModifiedAt(LocalDateTime.now());
    }

    public void delete() {
        this.isDeleted = Boolean.TRUE;
        this.setModifiedAt(LocalDateTime.now());
    }
}
