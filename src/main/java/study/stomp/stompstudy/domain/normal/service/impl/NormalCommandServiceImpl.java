package study.stomp.stompstudy.domain.normal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.stomp.stompstudy.domain.normal.domain.Normal;
import study.stomp.stompstudy.domain.normal.dto.request.NormalCreateRequest;
import study.stomp.stompstudy.domain.normal.dto.response.NormalInfoResponse;
import study.stomp.stompstudy.domain.normal.repository.NormalRepository;
import study.stomp.stompstudy.domain.normal.service.NormalCommandService;
import study.stomp.stompstudy.domain.user.repository.UserRepository;
import study.stomp.stompstudy.domain.user.service.UserCommandService;
import study.stomp.stompstudy.global.utils.SequenceGenerator;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NormalCommandServiceImpl implements NormalCommandService {

    private final SequenceGenerator sequenceGenerator;
    private final NormalRepository normalRepository;
    private final UserCommandService userCommandService;

    @Override
    public NormalInfoResponse save(NormalCreateRequest request) {
        Normal normal = Normal.from(request);

        normal.generateSequence(sequenceGenerator.generateSequence(Normal.SEQUENCE_NAME));

        userCommandService.addChatRoom(request.getUserCodes(), normal.getNormalId());

        normalRepository.save(normal);

        return NormalInfoResponse.from(normal);
    }
}
