package study.stomp.stompstudy.domain.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import study.stomp.stompstudy.domain.model.BaseModel;
import study.stomp.stompstudy.domain.model.RoleType;
import study.stomp.stompstudy.domain.user.dto.request.UserCreateRequest;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "users")
public class User extends BaseModel {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private Long userId;

    @Field
    @Indexed(unique = true)
    private String loginId;

    @Field
    private String nickName;

    @Field
    private String profileImg;

    @Field
    private String name;

    @Field
    private String password;

    @Field
    private boolean isDeleted = Boolean.FALSE;

    @Field
    private RoleType roleType;

    public static User from(UserCreateRequest request) {
        User user = new User();

        user.modifyUser(request);

        return  user;
    }

    private void modifyUser(UserCreateRequest request) {
        this.loginId = request.getLoginId();
        this.nickName = request.getNickName();
        this.name = request.getName();
        this.profileImg = request.getProfileImg();
        this.password = request.getPassword();
        this.roleType = RoleType.USER;
    }

    public void generateSequence(Long userId){
        this.userId = userId;
    }
}
