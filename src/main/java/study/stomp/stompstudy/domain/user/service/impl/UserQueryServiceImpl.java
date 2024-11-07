package study.stomp.stompstudy.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.stomp.stompstudy.domain.user.domain.User;
import study.stomp.stompstudy.domain.user.dto.response.UserInfoResponse;
import study.stomp.stompstudy.domain.user.exception.UserException;
import study.stomp.stompstudy.domain.user.repository.UserRepository;
import study.stomp.stompstudy.domain.user.service.UserQueryService;
import study.stomp.stompstudy.global.exception.Code;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    @Override
    public UserInfoResponse read(Long userId) {
        User user = validateUser(userId);

        return UserInfoResponse.from(user);
    }

    @Override
    public String getUserCode(Long userId) {
        User user = validateUser(userId);

        return user.getUserCode();
    }


    private User validateUser(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(Code.NOT_FOUND, "User Not Found"));
    }
}
