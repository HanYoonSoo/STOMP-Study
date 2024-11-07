package study.stomp.stompstudy.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserPwLostRequest {

    @NotBlank
    private String loginId;
}
