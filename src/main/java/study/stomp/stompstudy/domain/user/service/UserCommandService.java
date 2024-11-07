package study.stomp.stompstudy.domain.user.service;

import study.stomp.stompstudy.domain.user.dto.request.*;
import study.stomp.stompstudy.domain.user.dto.response.UserInfoResponse;

import java.util.List;

public interface UserCommandService {

    UserInfoResponse save(UserCreateRequest request);

    UserInfoResponse modify(UserModifyRequest request);

    void delete(UserDeleteRequest request);
    void addChatRoom(List<String> userCodes, Long chatId);

    void modifyPassword(UserPwModifyRequest request);

    String lostPassword(UserPwLostRequest request);

}
