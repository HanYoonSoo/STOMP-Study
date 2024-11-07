package study.stomp.stompstudy.domain.user.dto.response;

import lombok.*;
import study.stomp.stompstudy.domain.normal.dto.response.NormalInfoResponse;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserChatListResponse {

    private List<NormalInfoResponse> normals;

    public static UserChatListResponse from(List<NormalInfoResponse> normals) {
        return UserChatListResponse.builder()
                .normals(normals)
                .build();
    }
}
