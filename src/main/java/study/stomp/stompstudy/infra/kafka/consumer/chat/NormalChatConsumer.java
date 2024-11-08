package study.stomp.stompstudy.infra.kafka.consumer.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import study.stomp.stompstudy.domain.message.normal.dto.response.NormalMessageCreateResponse;
import study.stomp.stompstudy.global.common.dto.response.DataResponseDto;
import study.stomp.stompstudy.infra.kafka.producer.chat.event.NormalChatEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class NormalChatConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.normal-chat}", groupId = "${spring.kafka.consumer.group-id.normal-chat}", containerFactory = "normalChatListenerContainerFactory")
    public void normalChatListener(NormalChatEvent chatEvent) {
        Long normalId = chatEvent.getNormalId();

        System.out.println(normalId);

        switch (chatEvent.getActionType()){
            case SEND -> {
                NormalMessageCreateResponse response = NormalMessageCreateResponse.from(chatEvent);
                messagingTemplate.convertAndSend("/topic/normal/" + normalId, DataResponseDto.from(response));
            }
        }
    }
}
