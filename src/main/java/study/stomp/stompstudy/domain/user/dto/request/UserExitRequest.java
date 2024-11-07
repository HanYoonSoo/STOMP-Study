package study.stomp.stompstudy.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class UserExitRequest {

    @NotNull
    private Long userId;

    @NotNull
    private List<Long> chatIds;
}
