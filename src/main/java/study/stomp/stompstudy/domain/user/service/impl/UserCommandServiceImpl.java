package study.stomp.stompstudy.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.stomp.stompstudy.domain.user.domain.User;
import study.stomp.stompstudy.domain.user.dto.request.*;
import study.stomp.stompstudy.domain.user.dto.response.UserInfoResponse;
import study.stomp.stompstudy.domain.user.exception.UserException;
import study.stomp.stompstudy.domain.user.repository.UserRepository;
import study.stomp.stompstudy.domain.user.service.UserCommandService;
import study.stomp.stompstudy.global.exception.Code;
import study.stomp.stompstudy.global.utils.RandomUtil;
import study.stomp.stompstudy.global.utils.SequenceGenerator;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final SequenceGenerator sequenceGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserInfoResponse save(UserCreateRequest request) {
        existsLoginId(request.getLoginId());

        request.modifyPassword(passwordEncoder.encode(request.getPassword()));
        User user = User.from(request);
        user.generateSequence(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));

        return UserInfoResponse.from(userRepository.save(user));
    }

    @Override
    public UserInfoResponse modify(UserModifyRequest request) {
        User user = validateUser(request.getUserId());

        user.modify(request);

        return UserInfoResponse.from(userRepository.save(user));
    }

    @Override
    public void delete(UserDeleteRequest request) {
        User user = validateUser(request.getUserId());
        user.delete();
        userRepository.save(user);
    }

    @Override
    public void addChatRoom(List<String> userCodes, Long chatId) {
        for(String userCode : userCodes){
            addChatRoomToUser(userCode, chatId);
        }

    }

    @Override
    public void modifyPassword(UserPwModifyRequest request) {
        User user = validateLoginId(request.getLoginId());

        if(passwordEncoder.matches(request.getOldPassword(), user.getPassword()))
            user.modifyPassword(passwordEncoder.encode(request.getNewPassword()));
        else
            throw new UserException(Code.VALIDATION_ERROR, "User Password Not Match");

        userRepository.save(user);
    }

    @Override
    public String lostPassword(UserPwLostRequest request) {
        User user = validateLoginId(request.getLoginId());

        String newPassword = RandomUtil.generateRandomCode(12);
        user.modifyPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        return newPassword;
    }

    private void addChatRoomToUser(String userCode, Long chatId){
        User user = userRepository.findByUserCode(userCode)
                .orElseThrow(() -> new UserException(Code.NOT_FOUND, "UserCode Not Found"));

        user.getChatIds().add(chatId);

        userRepository.save(user);
    }


    private void existsLoginId(String loginId) {
        if(userRepository.existsByLoginIdAndIsDeletedFalse(loginId))
            throw new UserException(Code.VALIDATION_ERROR, "User LoginId 중복");
    }

    private User validateLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserException(Code.NOT_FOUND, "User Not Found"));
    }

    private User validateUser(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(Code.NOT_FOUND, "User Not Found"));
    }
}
