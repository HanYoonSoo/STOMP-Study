package study.stomp.stompstudy.domain.message.normal.service;

import org.springframework.data.domain.Slice;
import study.stomp.stompstudy.domain.message.normal.dto.NormalMessageDto;

public interface NormalMessageQueryService {

    Slice<NormalMessageDto> getMessages(Long normalId, int page, int size);
}
