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

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
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


}
