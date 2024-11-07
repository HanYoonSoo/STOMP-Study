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
import study.stomp.stompstudy.domain.user.dto.request.UserModifyRequest;
import study.stomp.stompstudy.global.utils.RandomUtil;
import study.stomp.stompstudy.global.utils.UUIDUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "users")
public class User extends BaseModel {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private Long userId;

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

    @Indexed(unique = true)
    private String userCode;

    @Field
    private Boolean isDeleted = Boolean.FALSE;

    @Field
    private RoleType roleType;

    @Field
    private List<Long> chatIds = new ArrayList<>();


    public static User from(UserCreateRequest request) {
        User user = new User();

        user.initUser(request);

        return  user;
    }

    private void initUser(UserCreateRequest request) {
        this.loginId = request.getLoginId();
        this.nickName = request.getNickName();
        this.name = request.getName();
        this.profileImg = request.getProfileImg();
        this.password = request.getPassword();
        this.roleType = RoleType.USER;
        this.setCreatedAt(LocalDateTime.now());
        this.setModifiedAt(LocalDateTime.now());
        this.userCode = RandomUtil.generateRandomCode(12);
    }

    public void modify(UserModifyRequest request) {
        this.nickName = request.getNickName();
        this.name = request.getName();
        this.profileImg = request.getProfileImg();
        this.setModifiedAt(LocalDateTime.now());
    }

    public void modifyPassword(String newPassword){
        this.password = newPassword;
    }

    public void delete(){
        this.isDeleted = Boolean.TRUE;
        this.setModifiedAt(LocalDateTime.now());
    }

    public void generateSequence(Long userId){
        this.userId = userId;
    }


}
