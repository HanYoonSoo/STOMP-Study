package study.stomp.stompstudy.global.common.dto.security;

import lombok.*;
import study.stomp.stompstudy.global.security.custom.CustomUserDetails;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse{

    private Long userId;
    private String loginId;
    private String nickName;
    private String profileImg;

    public static LoginResponse from(CustomUserDetails customUserDetails){
        return LoginResponse.builder()
                .userId(customUserDetails.getId())
                .loginId(customUserDetails.getLoginId())
                .nickName(customUserDetails.getNickName())
                .profileImg(customUserDetails.getProfileImg())
                .build();
    }
}