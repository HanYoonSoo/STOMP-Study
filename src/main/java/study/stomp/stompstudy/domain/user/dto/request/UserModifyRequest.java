package study.stomp.stompstudy.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserModifyRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String nickName;

    @NotBlank
    private String name;

    @NotBlank
    private String profileImg;
}
