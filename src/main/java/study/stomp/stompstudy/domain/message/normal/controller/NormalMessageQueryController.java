package study.stomp.stompstudy.domain.message.normal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.stomp.stompstudy.domain.message.normal.dto.NormalMessageDto;
import study.stomp.stompstudy.domain.message.normal.service.NormalMessageQueryService;
import study.stomp.stompstudy.global.common.dto.response.DataResponseDto;

@RestController
@RequiredArgsConstructor
public class NormalMessageQueryController {

    private final NormalMessageQueryService normalMessageQueryService;
    private final SimpMessageSendingOperations messagingTemplate;

    @GetMapping("/normal/messages/{normalId}")
    public DataResponseDto getMessages(@PathVariable("normalId") Long normalId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "30") int size)
    {
        Slice<NormalMessageDto> response = normalMessageQueryService.getMessages(normalId, page, size);

        return DataResponseDto.from(response);
    }

    // 클라이언트가 /topic/normal/{normalId}를 구독할 때 초기 메시지 전송
    @SubscribeMapping("/topic/normal/{normalId}")
    public void getMessages(@DestinationVariable Long normalId) {
        // 구독한 클라이언트만 대상으로 초기 메시지를 보냅니다.
        Slice<NormalMessageDto> response = normalMessageQueryService.getMessages(normalId, 0, 30);

        // 초기 데이터를 구독한 클라이언트에게만 전송
        messagingTemplate.convertAndSend("/topic/normal/" + normalId, DataResponseDto.from(response));
    }
}
