package study.stomp.stompstudy.domain.message.normal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import study.stomp.stompstudy.domain.message.normal.domain.NormalMessage;
import study.stomp.stompstudy.domain.message.normal.dto.NormalMessageDto;
import study.stomp.stompstudy.domain.message.normal.repository.NormalMessageRepository;
import study.stomp.stompstudy.domain.message.normal.service.NormalMessageQueryService;

@Slf4j
@Service
@RequiredArgsConstructor
public class NormalMessageQueryServiceImpl implements NormalMessageQueryService {

    private final NormalMessageRepository normalMessageRepository;

    @Override
    public Slice<NormalMessageDto> getMessages(Long normalId, int page, int size) {
        Slice<NormalMessageDto> messageDtos = messageToMessageDtos(normalId, page, size);

        return messageDtos;
    }

    private Slice<NormalMessageDto> messageToMessageDtos(Long normalId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Slice<NormalMessage> messages = normalMessageRepository.findByNormalIdAndIsDeleted(normalId, pageable);

        return messages.map(NormalMessageDto::from);
    }
}
