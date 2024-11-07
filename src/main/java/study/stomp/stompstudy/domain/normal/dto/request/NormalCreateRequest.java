package study.stomp.stompstudy.domain.normal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class NormalCreateRequest {

    @NotNull
    private String normalChatName;

    @NotNull
    private List<String> userIds;
}
