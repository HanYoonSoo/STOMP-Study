package study.stomp.stompstudy.domain.user.service;

import study.stomp.stompstudy.domain.user.dto.response.UserChatListResponse;
import study.stomp.stompstudy.domain.user.dto.response.UserInfoResponse;

public interface UserQueryService {

    UserInfoResponse read(Long userId);
    String getUserCode(Long userId);

    UserChatListResponse readUserChatList(Long userId);
}
