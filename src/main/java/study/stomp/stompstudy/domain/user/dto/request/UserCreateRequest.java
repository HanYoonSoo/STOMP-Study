package study.stomp.stompstudy.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UserCreateRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String nickName;

    @NotBlank
    private String name;

    private String profileImg;

    @NotBlank
    private String password;

    public void modifyPassword(String password){
        this.password = password;
    }
}
