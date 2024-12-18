package study.stomp.stompstudy.domain.message.normal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.stomp.stompstudy.domain.message.normal.domain.NormalMessage;
import study.stomp.stompstudy.domain.message.normal.dto.event.NormalChatCreateEvent;
import study.stomp.stompstudy.domain.message.normal.dto.event.NormalChatDeleteEvent;
import study.stomp.stompstudy.domain.message.normal.dto.event.NormalChatModifyEvent;
import study.stomp.stompstudy.domain.message.normal.dto.request.NormalMessageCreateRequest;
import study.stomp.stompstudy.domain.message.normal.dto.request.NormalMessageDeleteRequest;
import study.stomp.stompstudy.domain.message.normal.dto.request.NormalMessageModifyRequest;
import study.stomp.stompstudy.domain.message.normal.exception.NormalChatException;
import study.stomp.stompstudy.domain.message.normal.repository.NormalMessageRepository;
import study.stomp.stompstudy.domain.message.normal.service.NormalMessageCommandService;
import study.stomp.stompstudy.domain.normal.repository.NormalRepository;
import study.stomp.stompstudy.global.config.event.Events;
import study.stomp.stompstudy.global.exception.Code;
import study.stomp.stompstudy.global.utils.SequenceGenerator;
import study.stomp.stompstudy.global.utils.UUIDUtil;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NormalMessageCommandServiceImpl implements NormalMessageCommandService {

    private final SequenceGenerator sequenceGenerator;
    private final NormalMessageRepository messageRepository;
    private final NormalRepository normalRepository;

    @Override
    @Transactional
    public void save(NormalMessageCreateRequest request) {
        validateUserInNormal(request.getNormalId(), request.getUserId());
        NormalMessage normalMessage = NormalMessage.from(request);

        normalMessage.generateSequence(sequenceGenerator.generateSequence(NormalMessage.SEQUENCE_NAME));

        NormalChatCreateEvent chatCreateEvent =
                NormalChatCreateEvent.from(messageRepository.save(normalMessage), UUIDUtil.generateUUID());

        Events.send(chatCreateEvent);
    }

    @Override
    @Transactional
    public void modify(NormalMessageModifyRequest request) {
        validateUserInNormal(request.getNormalId(), request.getUserId());

        NormalMessage normalMessage = messageRepository.findById(request.getMessageId())
                .orElseThrow(() -> new NormalChatException(Code.NOT_FOUND, "Message Not Found"));

        normalMessage.modify(request.getContent());

//        System.out.println(request.getContent());

        NormalChatModifyEvent chatModifyEvent =
                NormalChatModifyEvent.from(messageRepository.save(normalMessage), UUIDUtil.generateUUID());

        Events.send(chatModifyEvent);
    }

    @Override
    @Transactional
    public void delete(NormalMessageDeleteRequest request) {
        validateUserInNormal(request.getNormalId(), request.getUserId());
        NormalMessage normalMessage = messageRepository.findById(request.getMessageId())
                .orElseThrow(() -> new NormalChatException(Code.NOT_FOUND, "Message Not Found"));

        normalMessage.delete();

        NormalChatDeleteEvent chatDeleteEvent =
                NormalChatDeleteEvent.from(messageRepository.save(normalMessage), UUIDUtil.generateUUID());

        Events.send(chatDeleteEvent);
    }


    private void validateUserInNormal(Long normalId, Long userId){
        normalRepository.findByNormalIdAndInUserId(normalId, List.of(userId))
                .orElseThrow(() -> new NormalChatException(Code.NOT_FOUND, "User Not In Normal Chat"));
    }
}
