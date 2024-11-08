package study.stomp.stompstudy.domain.message.normal.service;

import study.stomp.stompstudy.domain.message.normal.domain.NormalMessage;
import study.stomp.stompstudy.domain.message.normal.dto.NormalMessageDto;
import study.stomp.stompstudy.domain.message.normal.dto.request.NormalMessageCreateRequest;
import study.stomp.stompstudy.domain.message.normal.dto.request.NormalMessageDeleteRequest;
import study.stomp.stompstudy.domain.message.normal.dto.request.NormalMessageModifyRequest;

public interface NormalMessageCommandService {

    void save(NormalMessageCreateRequest request);

    void modify(NormalMessageModifyRequest request);

    void delete(NormalMessageDeleteRequest request);

}
