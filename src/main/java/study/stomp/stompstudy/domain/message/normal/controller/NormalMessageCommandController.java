package study.stomp.stompstudy.domain.message.normal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.RestController;
import study.stomp.stompstudy.domain.message.normal.dto.request.NormalMessageCreateRequest;
import study.stomp.stompstudy.domain.message.normal.dto.request.NormalMessageDeleteRequest;
import study.stomp.stompstudy.domain.message.normal.dto.request.NormalMessageModifyRequest;
import study.stomp.stompstudy.domain.message.normal.service.NormalMessageCommandService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NormalMessageCommandController {

    private final NormalMessageCommandService commandService;

    @MessageMapping("/normal/message/send")
    public void save(NormalMessageCreateRequest request){
        commandService.save(request);
    }

    @MessageMapping("/normal/message/modify")
    public void modify(NormalMessageModifyRequest request){
        commandService.modify(request);
    }

    @MessageMapping("/normal/message/delete")
    public void delete(NormalMessageDeleteRequest request){
        commandService.delete(request);
    }


}
