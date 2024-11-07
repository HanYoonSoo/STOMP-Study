package study.stomp.stompstudy.domain.normal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class NormalModifyRequest {

    @NotNull
    private Long normalId;

    @NotBlank
    private String normalChatName;
}
