package study.stomp.stompstudy.domain.user.service;

import study.stomp.stompstudy.domain.user.dto.request.UserCreateRequest;
import study.stomp.stompstudy.domain.user.dto.response.UserInfoResponse;

public interface UserCommandService {

    UserInfoResponse save(UserCreateRequest request);
}
