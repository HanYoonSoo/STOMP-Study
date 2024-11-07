package study.stomp.stompstudy.domain.normal.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;
import study.stomp.stompstudy.domain.model.BaseModel;
import study.stomp.stompstudy.domain.normal.dto.request.NormalCreateRequest;

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
    private List<String> userCodes = new ArrayList<>();

    public static Normal from(NormalCreateRequest request) {
        Normal normal = new Normal();

        normal.modifyNormal(request);

        return normal;
    }

    private void modifyNormal(NormalCreateRequest request){
        this.normalChatName = request.getNormalChatName();
        this.userCodes = request.getUserCodes();
    }

    public void generateSequence(Long normalId){
        this.normalId = normalId;
    }
}
