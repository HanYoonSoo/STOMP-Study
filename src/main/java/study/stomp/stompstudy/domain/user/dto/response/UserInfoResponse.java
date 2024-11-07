package study.stomp.stompstudy.domain.user.dto.response;

import lombok.*;
import study.stomp.stompstudy.domain.user.domain.User;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoResponse {

    private Long userId;

    private String nickName;

    private String name;

    private String profileImg;

    private String userCode;

    public static UserInfoResponse from(User user){
        return UserInfoResponse.builder()
                .userId(user.getUserId())
                .nickName(user.getNickName())
                .name(user.getName())
                .profileImg(user.getProfileImg())
                .userCode(user.getUserCode())
                .build();
    }
}
