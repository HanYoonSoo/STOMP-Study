package study.stomp.stompstudy.domain.normal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class NormalAddUserRequest {

    @NotNull
    private Long normalId;

    @NotNull
    private List<String> userCodes;
}
