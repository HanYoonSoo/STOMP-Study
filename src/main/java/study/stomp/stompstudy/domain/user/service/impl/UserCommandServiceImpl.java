package study.stomp.stompstudy.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.stomp.stompstudy.domain.user.domain.User;
import study.stomp.stompstudy.domain.user.dto.request.UserCreateRequest;
import study.stomp.stompstudy.domain.user.dto.response.UserInfoResponse;
import study.stomp.stompstudy.domain.user.exception.UserException;
import study.stomp.stompstudy.domain.user.repository.UserRepository;
import study.stomp.stompstudy.domain.user.service.UserCommandService;
import study.stomp.stompstudy.global.exception.Code;
import study.stomp.stompstudy.global.utils.SequenceGenerator;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final SequenceGenerator sequenceGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserInfoResponse save(UserCreateRequest request) {
        validateLoginId(request.getLoginId());

        request.modifyPassword(passwordEncoder.encode(request.getPassword()));

        User user = User.from(request);

        user.generateSequence(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));


        userRepository.save(user);

        return UserInfoResponse.from(user);
    }

    private void validateLoginId(String loginId) {
        if(userRepository.existsByLoginIdAndIsDeletedFalse(loginId))
            throw new UserException(Code.VALIDATION_ERROR, "User LoginId 중복");
    }
}
