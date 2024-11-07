package study.stomp.stompstudy.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.stomp.stompstudy.domain.normal.dto.response.NormalInfoResponse;
import study.stomp.stompstudy.domain.normal.repository.NormalRepository;
import study.stomp.stompstudy.domain.user.domain.User;
import study.stomp.stompstudy.domain.user.dto.response.UserChatListResponse;
import study.stomp.stompstudy.domain.user.dto.response.UserInfoResponse;
import study.stomp.stompstudy.domain.user.exception.UserException;
import study.stomp.stompstudy.domain.user.repository.UserRepository;
import study.stomp.stompstudy.domain.user.service.UserQueryService;
import study.stomp.stompstudy.global.exception.Code;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final NormalRepository normalRepository;

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

    public UserChatListResponse readUserChatList(Long userId) {
        User user = validateUser(userId);

        List<Long> normalChatIds = user.getChatIds();

        List<NormalInfoResponse> normals = normalRepository.findAllById(normalChatIds).stream()
                .map(NormalInfoResponse::from)
                .collect(Collectors.toList());

        return UserChatListResponse.from(normals);
    }


    private User validateUser(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(Code.NOT_FOUND, "User Not Found"));
    }
}
