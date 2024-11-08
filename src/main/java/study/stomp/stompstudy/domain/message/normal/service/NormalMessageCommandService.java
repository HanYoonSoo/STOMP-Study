package study.stomp.stompstudy.domain.message.normal.service;

import study.stomp.stompstudy.domain.message.normal.domain.NormalMessage;
import study.stomp.stompstudy.domain.message.normal.dto.NormalMessageDto;
import study.stomp.stompstudy.domain.message.normal.dto.request.NormalMessageCreateRequest;

public interface NormalMessageCommandService {

    void save(NormalMessageCreateRequest request);

}
