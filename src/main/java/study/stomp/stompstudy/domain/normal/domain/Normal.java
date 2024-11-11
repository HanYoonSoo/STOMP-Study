package study.stomp.stompstudy.domain.normal.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;
import study.stomp.stompstudy.domain.model.BaseModel;
import study.stomp.stompstudy.domain.normal.dto.request.NormalCreateRequest;
import study.stomp.stompstudy.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Normal extends BaseModel {

    @Transient
    public static final String SEQUENCE_NAME = "normal_sequence";

    @Id
    private Long normalId;

    @Field
    private String normalChatName;

    @Field
    private List<Long> userIds = new ArrayList<>();

    @Field
    private Boolean isDeleted = Boolean.FALSE;

    public static Normal from(NormalCreateRequest request) {
        Normal normal = new Normal();

        normal.initNormal(request);

        return normal;
    }

    private void initNormal(NormalCreateRequest request){
        this.normalChatName = request.getNormalChatName();
        this.setCreatedAt(LocalDateTime.now());
        this.setModifiedAt(LocalDateTime.now());
    }


    public void modifyChatName(String normalChatName) {
        this.normalChatName = normalChatName;
        this.setModifiedAt(LocalDateTime.now());
    }

    public void generateSequence(Long normalId){
        this.normalId = normalId;
    }


    public void removeUser(User user) {
        this.userIds.remove(user.getUserId());
        user.getChatIds().remove(normalId);
    }
}
