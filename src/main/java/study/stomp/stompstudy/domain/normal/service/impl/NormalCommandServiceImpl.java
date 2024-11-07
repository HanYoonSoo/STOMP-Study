package study.stomp.stompstudy.domain.normal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.stomp.stompstudy.domain.normal.domain.Normal;
import study.stomp.stompstudy.domain.normal.dto.request.NormalAddUserRequest;
import study.stomp.stompstudy.domain.normal.dto.request.NormalCreateRequest;
import study.stomp.stompstudy.domain.normal.dto.request.NormalModifyRequest;
import study.stomp.stompstudy.domain.normal.dto.response.NormalInfoResponse;
import study.stomp.stompstudy.domain.normal.exception.NormalException;
import study.stomp.stompstudy.domain.normal.repository.NormalRepository;
import study.stomp.stompstudy.domain.normal.service.NormalCommandService;
import study.stomp.stompstudy.domain.user.domain.User;
import study.stomp.stompstudy.domain.user.exception.UserException;
import study.stomp.stompstudy.domain.user.repository.UserRepository;
import study.stomp.stompstudy.domain.user.service.UserCommandService;
import study.stomp.stompstudy.global.exception.Code;
import study.stomp.stompstudy.global.utils.SequenceGenerator;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NormalCommandServiceImpl implements NormalCommandService {

    private final SequenceGenerator sequenceGenerator;
    private final NormalRepository normalRepository;
    private final UserRepository userRepository;

    @Override
    public NormalInfoResponse save(NormalCreateRequest request) {
        Normal normal = Normal.from(request);

        normal.generateSequence(sequenceGenerator.generateSequence(Normal.SEQUENCE_NAME));

        this.addChatRoom(request.getUserCodes(), normal);

        normalRepository.save(normal);

        return NormalInfoResponse.from(normal);
    }

    @Override
    public NormalInfoResponse modify(NormalModifyRequest request) {
        Normal normal = validateNormal(request.getNormalId());

        normal.modifyChatName(request.getNormalChatName());

        return NormalInfoResponse.from(normalRepository.save(normal));
    }

    @Override
    public void addUser(NormalAddUserRequest request) {
        Normal normal = validateNormal(request.getNormalId());

        this.addChatRoom(request.getUserCodes(), normal);
    }

    private Normal validateNormal(Long normalId) {
        return normalRepository.findByNormalId(normalId)
                .orElseThrow(() -> new UserException(Code.NOT_FOUND, "Normal Not Found"));
    }

    private void addChatRoom(List<String> userCodes, Normal normal) {
        List<User> users = userRepository
                .findAllByUserCodesAndNotInNormalId(userCodes, List.of(normal.getNormalId()));

        for(User user : users){
            user.addChat(normal);
            userRepository.save(user);
        }
    }
}
