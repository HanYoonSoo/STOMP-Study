package study.stomp.stompstudy.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserPwModifyRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
